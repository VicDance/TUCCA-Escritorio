/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connector.Clave;
import connector.Conector;
import connector.Encriptar;
import dao.ClienteDAOImp;
import dao.UsuarioDAOImp;
import dao.LineaDAOImp;
import dao.MunicipioDAOImp;
import dao.NucleoDAOImp;
import dao.ParadaDAOImp;
import dao.PuntoVentaDAOImp;
import dao.TarjetaBusDAOImp;
import dao.TarjetaCreditoDAOImp;
import dao.TarjetasBusDAOImp;
import dao.ZonaDAOImp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Linea;
import model.Municipio;
import model.Nucleo;
import model.Parada;
import model.PuntoVenta;
import model.TarjetaBus;
import model.TarjetaCredito;
import model.TarjetaEstandar;
import model.TarjetaEstudiante;
import model.TarjetaJubilado;
import model.Usuario;
import model.Zona;
import scripts.Corresponde;

/**
 *
 * @author Vicky
 */
public class HiloServidorBahiaCadiz extends Thread implements Clave {

    private final Socket cliente;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private Conector con;
    private Encriptar encriptar;
    private List<Usuario> usuarios;
    private List<Linea> lineas;
    private List<Parada> paradas;
    private List<Municipio> municipios;
    private List<Nucleo> nucleos;
    private List<Zona> zonas;
    private List<TarjetaCredito> tarjetas;
    private List<Usuario> clientes;
    private List<TarjetaEstandar> estandares;
    private List<TarjetaEstudiante> estudiantes;
    private List<TarjetaJubilado> jubilados;
    private UsuarioDAOImp udi;
    private LineaDAOImp ldi;
    private ParadaDAOImp pdi;
    private MunicipioDAOImp mdi;
    private NucleoDAOImp ndi;
    private ZonaDAOImp zdi;
    //private ClienteDAOImp cdi;
    private TarjetaCreditoDAOImp tcdi;
    private TarjetaBusDAOImp tbdi;
    private TarjetasBusDAOImp tsbdi;
    private String texto;
    private String[] datos;
    private Usuario usuario;
    private TarjetaBus tarjetaBus;
    private long numAbsoluto;
    private String nombreUsuario;
    private String contraseña;

    public HiloServidorBahiaCadiz(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            dataOut = new DataOutputStream(cliente.getOutputStream());
            dataIn = new DataInputStream(cliente.getInputStream());
            con = new Conector();
            con.connect();
            while (true) {
                System.out.println("servidor escuchando");
                String cadena = dataIn.readUTF();
                System.out.println("cadena: " + cadena);
                udi = new UsuarioDAOImp(con);
                switch (cadena) {
                    case "encriptar":
                        texto = dataIn.readUTF();
                        System.out.println(texto);
                        datos = texto.split("/");
                        String encriptada;
                        encriptar = new Encriptar();
                        if (datos.length == 5) {
                            encriptada = encriptar.encriptar(datos[1], CLAVE);
                            usuario = new Usuario(datos[0], encriptada, datos[2], new Date(Long.parseLong(datos[4])),
                                    Integer.parseInt(datos[3]));
                            udi.insertar(usuario);
                            Usuario us = udi.getId(datos[0]);
                            udi.insertarAdmin(us.getId());
                            if (udi.insertado) {
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                dataOut.writeUTF("incorrecto");
                                dataOut.flush();
                            }
                        } else {
                            if (datos[0].equalsIgnoreCase("cliente")) {
                                encriptada = encriptar.encriptar(datos[2], CLAVE);
                                usuario = new Usuario(datos[1], encriptada, datos[3], new Date(Long.parseLong(datos[5])),
                                        Integer.parseInt(datos[4]));
                                udi.insertar(usuario);
                                Usuario us = udi.getId(datos[1]);
                                //cdi = new ClienteDAOImp();
                                udi.insertarCliente(us.getId());
                                if (udi.insertado) {
                                    dataOut.writeUTF("correcto");
                                    dataOut.flush();
                                } else {
                                    dataOut.writeUTF("incorrecto");
                                    dataOut.flush();
                                }
                            }
                        }
                        break;
                    case "inicio":
                        nombreUsuario = dataIn.readUTF();
                        contraseña = dataIn.readUTF();
                        comprobarCredenciales(cadena, nombreUsuario, contraseña);
                        break;

                    case "inicioAdmin":
                        nombreUsuario = dataIn.readUTF();
                        contraseña = dataIn.readUTF();
                        comprobarCredenciales(cadena, nombreUsuario, contraseña);
                        break;

                    case "usuarios":
                        usuarios = udi.getAll();
                        dataOut.writeInt(usuarios.size());
                        dataOut.flush();
                        System.out.println(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            dataOut.writeUTF(usuario1.getId() + "/" + usuario1.getNombre() + "/" + usuario1.getCorreo() + "/"
                                    + usuario1.getFecha_nac() + "/" + usuario1.getTfno());
                            dataOut.flush();
                        }
                        break;

                    case "clientes":
                        usuarios = udi.getAllClientes();
                        dataOut.writeInt(usuarios.size());
                        dataOut.flush();
                        //System.out.println(clientes.size());
                        for (Usuario cli : usuarios) {
                            dataOut.writeUTF(cli.getId() + "/" + cli.getNombre() + "/" + cli.getCorreo() + "/"
                                    + cli.getFecha_nac() + "/" + cli.getTfno());
                            dataOut.flush();
                        }
                        break;

                    case "lineas":
                        ldi = new LineaDAOImp(con);
                        lineas = ldi.getAllLineas();
                        dataOut.writeInt(lineas.size());
                        dataOut.flush();
                        for (Linea linea : lineas) {
                            dataOut.writeUTF(linea.getIdLinea() + "/" + linea.getNombreLinea());
                            dataOut.flush();
                        }
                        break;

                    case "paradas":
                        pdi = new ParadaDAOImp(con);
                        paradas = pdi.getAllParadas();
                        dataOut.writeInt(paradas.size());
                        dataOut.flush();
                        for (Parada parada : paradas) {
                            dataOut.writeUTF(parada.getIdParada() + "/" + parada.getIdZona()
                                    + "/" + parada.getNombreParada() + "/" + parada.getLatitud() + "/"
                                    + parada.getLongitud());
                            dataOut.flush();
                        }
                        break;

                    case "paradas_viaje":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        pdi = new ParadaDAOImp(con);
                        ldi = new LineaDAOImp(con);
                        lineas = ldi.getLineasNucleo(Integer.parseInt(datos[1]), Integer.parseInt(datos[3]));
                        dataOut.writeInt(lineas.size());
                        dataOut.flush();
                        //int[] stringParadas;
                        for (Linea linea : lineas) {
                            String l = ldi.getNombreLinea(linea.getIdLinea());
                            dataOut.writeUTF(l);
                            dataOut.flush();
                            paradas = pdi.getParadasViaje(linea.getIdLinea(), Integer.parseInt(datos[1]), Integer.parseInt(datos[3]));
                            System.out.println(l);
                            dataOut.writeInt(paradas.size());
                            dataOut.flush();
                            for (Parada parada : paradas) {
                                System.out.println(parada);
                                //System.out.println("sale bucle");
                                //String idParada
                                dataOut.writeUTF(parada.getIdParada() + "/" + parada.getIdZona()
                                        + "/" + parada.getNombreParada() + "/" + parada.getLatitud() + "/"
                                        + parada.getLongitud());
                                dataOut.flush();
                            }
                            /*System.out.println("NombreLinea: " + l);
                             for(Parada parada : paradas){
                             System.out.println(parada);
                             System.out.println("sale bucle");
                             }*/
                            //
                            /*dataOut.writeUTF(parada.getIdParada() + "/" + parada.getIdZona()
                             + "/" + parada.getNombreParada() + "/" + parada.getLatitud() + "/"
                             + parada.getLongitud());
                             dataOut.flush();*/
                        }
                        break;

                    case "municipios":
                        mdi = new MunicipioDAOImp(con);
                        municipios = mdi.getAllMunicipios();
                        dataOut.writeInt(municipios.size());
                        dataOut.flush();
                        for (Municipio muni : municipios) {
                            dataOut.writeUTF(muni.getIdMunicipio() + "/" + muni.getNombreMunicipio());
                            dataOut.flush();
                        }
                        break;

                    case "nucleos":
                        ndi = new NucleoDAOImp(con);
                        nucleos = ndi.getAllNucleos();
                        dataOut.writeInt(nucleos.size());
                        dataOut.flush();
                        for (Nucleo nucleo : nucleos) {
                            dataOut.writeUTF(nucleo.getIdNucleo() + "/" + nucleo.getIdMunicipio() + "/"
                                    + nucleo.getIdZona() + "/" + nucleo.getNombreNucleo());
                            dataOut.flush();
                        }
                        break;

                    case "zonas":
                        //System.out.println("entra zonas");
                        zdi = new ZonaDAOImp(con);
                        zonas = zdi.getAll();
                        dataOut.writeInt(zonas.size());
                        dataOut.flush();
                        for (Zona zona : zonas) {
                            dataOut.writeUTF(zona.getIdZona() + "/" + zona.getNombreZona());
                            dataOut.flush();
                        }
                        break;

                    case "tarjetas":
                        //System.out.println("entra");
                        tcdi = new TarjetaCreditoDAOImp(con);
                        tarjetas = tcdi.getAllTarjetas();
                        System.out.println(tarjetas.size());
                        dataOut.writeInt(tarjetas.size());
                        dataOut.flush();
                        for (TarjetaCredito tarjeta : tarjetas) {
                            dataOut.writeUTF(tarjeta.getNumTarjeta() + "-" + tarjeta.getIdUser() + "-"
                                    + tarjeta.getCaducidad() + "-" + tarjeta.getTitular());
                            dataOut.flush();
                        }
                        break;

                    case "tarjetasb":
                        tsbdi = new TarjetasBusDAOImp(con);
                        estandares = tsbdi.getAllTarjetasEstandar();
                        estudiantes = tsbdi.getAllTarjetasEstudiante();
                        jubilados = tsbdi.getAllTarjetasJubilado();
                        System.out.println(estandares.size() + estudiantes.size() + jubilados.size());
                        dataOut.writeInt(estandares.size() + estudiantes.size() + jubilados.size());
                        dataOut.flush();
                        for (TarjetaEstandar tarjeta : estandares) {
                            dataOut.writeUTF(tarjeta.getNumTarjeta() + "/" + tarjeta.getFecha_expedicion()
                                    + "/" + "estandar");
                            dataOut.flush();
                        }
                        for (TarjetaEstudiante tarjeta : estudiantes) {
                            dataOut.writeUTF(tarjeta.getNumTarjeta() + "/" + tarjeta.getFecha_ini()
                                    + "/" + tarjeta.getFecha_fin() + "/" + "estudiante");
                            dataOut.flush();
                        }
                        for (TarjetaJubilado tarjeta : jubilados) {
                            dataOut.writeUTF(tarjeta.getNumTarjeta() + "/" + tarjeta.getAñoValidez()
                                    + "/" + "jubilado");
                            dataOut.flush();
                        }
                        break;

                    case "puntos_venta":
                        ndi = new NucleoDAOImp(con);
                        nucleos = ndi.getAllNucleos();
                        //System.out.println(nucleos.size());
                        dataOut.writeInt(nucleos.size());
                        dataOut.flush();
                        for (int i = 0; i < nucleos.size(); i++) {
                            dataOut.writeUTF(nucleos.get(i).getNombreNucleo() + "/" + nucleos.get(i).getIdNucleo());
                            dataOut.flush();
                        }
                        break;

                    case "usuario":
                        texto = dataIn.readUTF();
                        usuarios = udi.getUsuario(texto);
                        dataOut.writeInt(usuarios.size());
                        dataOut.flush();
                        for (Usuario usuario1 : usuarios) {
                            dataOut.writeUTF(usuario1.getId() + "/" + usuario1.getNombre() + "/" + usuario1.getCorreo()
                                    + "/" + usuario1.getFecha_nac() + "/" + usuario1.getTfno());
                            dataOut.flush();
                        }
                        break;

                    case "linea":
                        texto = dataIn.readUTF();
                        lineas = ldi.getLinea(texto);
                        dataOut.writeInt(lineas.size());
                        dataOut.flush();
                        for (Linea linea : lineas) {
                            dataOut.writeUTF(linea.getIdLinea() + "/" + linea.getNombreLinea());
                            dataOut.flush();
                        }
                        break;

                    case "parada":
                        texto = dataIn.readUTF();
                        paradas = pdi.getParada(texto);
                        dataOut.writeInt(paradas.size());
                        dataOut.flush();
                        for (Parada parada : paradas) {
                            dataOut.writeUTF(parada.getIdParada() + "/" + parada.getIdZona() + "/"
                                    + parada.getNombreParada() + "/" + parada.getLatitud() + "/" + parada.getLongitud());
                            dataOut.flush();
                        }
                        break;

                    case "municipio":
                        texto = dataIn.readUTF();
                        municipios = mdi.getMunicipio(texto);
                        dataOut.writeInt(municipios.size());
                        dataOut.flush();
                        for (Municipio muni : municipios) {
                            dataOut.writeUTF(muni.getIdMunicipio() + "/" + muni.getNombreMunicipio());
                            dataOut.flush();
                        }
                        break;

                    case "nucleo":
                        texto = dataIn.readUTF();
                        nucleos = ndi.getNucleo(texto);
                        dataOut.writeInt(nucleos.size());
                        dataOut.flush();
                        for (Nucleo nucleo : nucleos) {
                            dataOut.writeUTF(nucleo.getIdNucleo() + "/" + nucleo.getIdMunicipio() + "/"
                                    + nucleo.getIdZona() + "/" + nucleo.getNombreNucleo());
                            dataOut.flush();
                        }
                        break;

                    case "zona":
                        texto = dataIn.readUTF();
                        zonas = zdi.getZona(texto);
                        dataOut.writeInt(zonas.size());
                        dataOut.flush();
                        for (Zona zona : zonas) {
                            dataOut.writeUTF(zona.getIdZona() + "/" + zona.getNombreZona());
                            dataOut.flush();
                        }
                        break;

                    case "cliente":
                        texto = dataIn.readUTF();
                        Usuario user = udi.getId(texto);
                        dataOut.writeUTF(user.getNombre() + "/" + user.getContraseña() + "/" + user.getCorreo() + "/"
                                + user.getFecha_nac() + "/" + user.getTfno());
                        break;

                    case "tarjeta":
                        texto = dataIn.readUTF();
                        //System.out.println(texto);
                        TarjetaBus bus = tsbdi.getTarjeta(Long.parseLong(texto));
                        dataOut.writeUTF(texto);
                        dataOut.flush();
                        dataOut.writeInt(bus.getSaldo());
                        dataOut.flush();
                        break;

                    case "puntos_venta_mapa":
                        int idNucleo = dataIn.readInt();
                        PuntoVentaDAOImp pvdi = new PuntoVentaDAOImp(con);
                        //System.out.println(idNucleo);
                        List<PuntoVenta> puntos = pvdi.getPuntosVentaNucleo(idNucleo);
                        dataOut.writeInt(puntos.size());
                        dataOut.flush();
                        for (int i = 0; i < puntos.size(); i++) {
                            //System.out.println(puntos.get(i).getLatitud() + "/" + puntos.get(i).getLongitud());
                            dataOut.writeUTF(puntos.get(i).getLatitud() + "/" + puntos.get(i).getLongitud());
                            dataOut.flush();
                        }
                        break;

                    case "rtarjeta":
                        int saldo = dataIn.readInt();
                        //System.out.println(saldo);
                        texto = dataIn.readUTF();
                        tsbdi.recargarTarjeta(Long.parseLong(texto), saldo);
                        System.out.println(tsbdi.recarga);
                        if (tsbdi.recarga) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "busuario":
                        int id = dataIn.readInt();
                        udi.borrar(id);
                        if (udi.borrado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "btarjetaBus":
                        int posicion = dataIn.readInt();
                        tsbdi = new TarjetasBusDAOImp(con);
                        tsbdi.borrarTarjeta(posicion);
                        /*tbdi = new TarjetaBusDAOImp(con);
                         tbdi.borrar(posicion);*/
                        if (tsbdi.borrado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "ntarjeta":
                        texto = dataIn.readUTF();
                        datos = texto.split("-");
                        TarjetaCredito tarjeta = new TarjetaCredito(datos[0], Integer.parseInt(datos[1]),
                                datos[2], datos[3]);
                        //tcdi = new TarjetaCreditoDAOImp();
                        tcdi.insertar(tarjeta);
                        //System.out.println(tcdi.insertado);
                        if (tcdi.insertado) {
                            System.out.println("entra");
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            System.out.println("entra tb");
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "tarjeta_es":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                        tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.1);
                        tsbdi = new TarjetasBusDAOImp(con);
                        //tbdi = new TarjetaBusDAOImp();
                        tsbdi.insertarTarjeta(tarjetaBus);
                        TarjetaEstandar es = new TarjetaEstandar(numAbsoluto);
                        tsbdi.insertarEstandar(es.getNumTarjeta());
                        if (tsbdi.insertado) {
                            //System.out.println("entra");
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            //System.out.println("entra tb");
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "tarjeta_ju":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        if (compruebaFechaJubilado(Integer.parseInt(datos[1]))) {
                            numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                            tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.5);
                            //tbdi = new TarjetaBusDAOImp();
                            tsbdi = new TarjetasBusDAOImp(con);
                            tsbdi.insertarTarjeta(tarjetaBus);
                            TarjetaJubilado jubilado = new TarjetaJubilado(numAbsoluto);
                            //tsbdi = new TarjetasBusDAOImp();
                            tsbdi.insertarJubilado(jubilado.getNumTarjeta(), getAñoValidez());
                            if (tsbdi.insertado) {
                                //System.out.println("entra");
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                //System.out.println("entra tb");
                                dataOut.writeUTF("incorrecto");
                                dataOut.flush();
                            }
                        } else {
                            dataOut.writeUTF("invalido");
                            dataOut.flush();
                        }
                        break;

                    case "tarjeta_estu":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        if (compruebaMes(Integer.parseInt(datos[1]))) {
                            numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                            tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.3);
                            //tbdi = new TarjetaBusDAOImp();
                            tsbdi = new TarjetasBusDAOImp(con);
                            tsbdi.insertarTarjeta(tarjetaBus);
                            Calendar calendarIni = Calendar.getInstance();
                            Calendar calendarFin = new GregorianCalendar();
                            calendarIni.set(Calendar.MONTH, 8);
                            calendarIni.set(Calendar.DAY_OF_MONTH, 1);
                            calendarFin.set(Calendar.MONTH, 5);
                            calendarFin.set(Calendar.DAY_OF_MONTH, 30);
                            java.util.Date dateIni = calendarIni.getTime();
                            java.sql.Date date2Ini = new java.sql.Date(dateIni.getTime());
                            java.util.Date dateFin = calendarFin.getTime();
                            java.sql.Date date2Fin = new java.sql.Date(dateFin.getTime());
                            TarjetaEstudiante estudiante = new TarjetaEstudiante(numAbsoluto,
                                    date2Ini,
                                    date2Fin);
                            //tsbdi = new TarjetasBusDAOImp();
                            //System.out.println(calendarFin.get(Calendar.YEAR));
                            tsbdi.insertarEstudiante(estudiante.getNumTarjeta(), estudiante.getFecha_ini(), estudiante.getFecha_fin());
                            if (tsbdi.insertado) {
                                //System.out.println("entra");
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                //System.out.println("entra tb");
                                dataOut.writeUTF("incorrecto");
                                dataOut.flush();
                            }
                        } else {
                            dataOut.writeUTF("invalido");
                            dataOut.flush();
                        }
                        break;

                    case "icliente":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        encriptar = new Encriptar();
                        String nuevaContraseña = encriptar.encriptar(datos[1], CLAVE);
                        Usuario us = new Usuario(datos[0], nuevaContraseña, datos[2], new Date(Long.parseLong(datos[4])),
                                Integer.parseInt(datos[3]));
                        udi.insertar(us);
                        if (udi.insertado) {
                            us = udi.getId(datos[0]);
                            udi.insertarCliente(us.getId());
                            if (udi.insertado) {
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            }
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "exit":
                        break;
                }
                if (cadena.trim().contains("exit")) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.disconect();
                cliente.close();
                dataIn.close();
                dataOut.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean comprobarCredenciales(String cadena, String usuario, String contraseña) {
        boolean correcto = false;
        usuarios = udi.getAll();
        //cdi = new ClienteDAOImp();
        clientes = udi.getAllClientes();
        encriptar = new Encriptar();
        List<Cliente> admins = udi.getAllAdmins();
        for (int i = 0; i < usuarios.size(); i++) {
            String convertida = encriptar.encriptar(contraseña, CLAVE);
            if (cadena.contains("Admin")) {
                for (int x = 0; x < admins.size(); x++) {
                    if (usuario.equals(usuarios.get(i).getNombre()) && convertida.equals(usuarios.get(i).getContraseña())
                            && admins.get(x).getIdCliente() == usuarios.get(i).getId()) {
                        correcto = true;
                        try {
                            //System.out.println("entra");
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                            break;
                        } catch (IOException ex) {
                            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                //break;
            } else {
                for (int j = 0; j < clientes.size(); j++) {
                    if (usuario.equals(usuarios.get(i).getNombre()) && convertida.equals(usuarios.get(i).getContraseña())
                            && clientes.get(j).getId() == usuarios.get(i).getId()) {
                        correcto = true;
                        try {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                            dataOut.writeInt(clientes.get(j).getId());
                            dataOut.flush();
                            break;
                        } catch (IOException ex) {
                            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        if (!correcto) {
            try {
                System.out.println("entra");
                dataOut.writeUTF("incorrecto");
                dataOut.flush();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return correcto;
    }

    private boolean compruebaFechaJubilado(int idCliente) {
        boolean correcto = false;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        java.util.Date d = new java.util.Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        calendar2.setTime(date2);
        int anioActual = calendar2.get(Calendar.YEAR);
        for (int i = 0; i < usuarios.size(); i++) {
            calendar.setTime(usuarios.get(i).getFecha_nac());
            int anioCliente = calendar.get(Calendar.YEAR);
            if ((anioActual - anioCliente >= 65) && idCliente == usuarios.get(i).getId()) {
                correcto = true;
            }
        }

        return correcto;
    }

    private int getAñoValidez() {
        int anio = 0;
        Calendar calendar = Calendar.getInstance();
        java.util.Date d = new java.util.Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        calendar.setTime(date2);
        anio = calendar.get(Calendar.YEAR) - 65;
        return anio;
    }

    private boolean compruebaMes(int idCliente) {
        boolean correcto = false;
        Calendar calendar = Calendar.getInstance();
        java.util.Date d = new java.util.Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        calendar.setTime(date2);
        int mesActual = calendar.get(Calendar.MONTH);
        for (int i = 0; i < usuarios.size(); i++) {
            if ((mesActual >= 9 || mesActual <= 6) && idCliente == usuarios.get(i).getId()) {
                correcto = true;
            }
        }
        return correcto;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connector.Clave;
import connector.Conector;
import connector.Encriptar;
import dao.CodigoDAOImp;
import dao.UsuarioDAOImp;
import dao.LineaDAOImp;
import dao.MunicipioDAOImp;
import dao.NucleoDAOImp;
import dao.ParadaDAOImp;
import dao.PuntoVentaDAOImp;
import dao.TarjetaBusDAOImp;
import dao.TarjetaCreditoDAOImp;
import dao.TarjetasBusDAOImp;
import dao.ViajeDAOImp;
import dao.ZonaDAOImp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import model.Cliente;
import model.CodigoQR;
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
import model.Viaje;
import model.Zona;
import scripts.Inserts;
import view.InsertForm;

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
    private List<Cliente> clientes;
    private List<TarjetaEstandar> estandares;
    private List<TarjetaEstudiante> estudiantes;
    private List<TarjetaJubilado> jubilados;
    private UsuarioDAOImp udi;
    private LineaDAOImp ldi;
    private ParadaDAOImp pdi;
    private MunicipioDAOImp mdi;
    private NucleoDAOImp ndi;
    private ZonaDAOImp zdi;
    private TarjetaCreditoDAOImp tcdi;
    private TarjetaBusDAOImp tbdi;
    private TarjetasBusDAOImp tsbdi;
    private CodigoDAOImp cdi;
    private ViajeDAOImp vdi;
    private String texto;
    private String[] datos;
    private Usuario usuario;
    private TarjetaBus tarjetaBus;
    private CodigoQR qr;
    private long numAbsoluto;
    private String nombreUsuario;
    private String contraseña;
    private int id;
    private DateFormat dateFormat;
    private java.util.Date horaActual;
    private CodigoQR codigo;
    private String horaCodigo;

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
                            } else {
                                encriptada = encriptar.encriptar(datos[2], CLAVE);
                                usuario = new Usuario(datos[1], encriptada, datos[3], new Date(Long.parseLong(datos[4])),
                                        Integer.parseInt(datos[5]));
                                udi.insertar(usuario);
                                Usuario us = udi.getId(datos[1]);
                                udi.insertarRevisor(us.getId());
                                if (udi.insertado) {
                                    dataOut.writeUTF("correcto");
                                    dataOut.flush();
                                } else {
                                    dataOut.writeUTF("incorrecto");
                                    dataOut.flush();
                                }

                                sendEmail(datos[3], datos[1], datos[2]);
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
                        //System.out.println(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            dataOut.writeUTF(usuario1.getId() + "/" + usuario1.getNombre() + "/" + usuario1.getCorreo() + "/"
                                    + usuario1.getFecha_nac() + "/" + usuario1.getTfno());
                            dataOut.flush();
                        }
                        break;

                    case "clientes":
                        clientes = udi.getAllClientes();
                        dataOut.writeInt(usuarios.size());
                        dataOut.flush();
                        //System.out.println(clientes.size());
                        for (Cliente cli : clientes) {
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

                    case "viajes":
                        int idUsuario = dataIn.readInt();
                        vdi = new ViajeDAOImp(con);
                        List<Viaje> viajes = vdi.getViajesId(idUsuario);
                        dataOut.writeInt(viajes.size());
                        dataOut.flush();
                        ldi = new LineaDAOImp(con);
                        mdi = new MunicipioDAOImp(con);
                        for (int i = 0; i < viajes.size(); i++) {
                            String nombreLinea = ldi.getNombreLinea(viajes.get(i).getIdLinea());
                            String nombreMunicipio = mdi.getNombreMunicipio(viajes.get(i).getIdMunicipio());
                            dataOut.writeUTF(nombreLinea + "/" + nombreMunicipio + "/"
                                    + viajes.get(i).getHoraSalida() + "/" + viajes.get(i).getFechaViaje().getTime());
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

                    case "id_linea":
                        texto = dataIn.readUTF();
                        int idLinea = ldi.getIdLinea(texto);
                        dataOut.writeInt(idLinea);
                        dataOut.flush();
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
                        //System.out.println(user);
                        if (user.getImagen() == null) {
                            dataOut.writeUTF(user.getId() + "¬" + user.getNombre() + "¬" + user.getContraseña() + "¬" + user.getCorreo() + "¬"
                                    + user.getFecha_nac() + "¬" + user.getTfno());
                            //dataOut.flush();
                        } else {
                            dataOut.writeUTF(user.getId() + "¬" + user.getNombre() + "¬" + user.getContraseña() + "¬" + user.getCorreo() + "¬"
                                    + user.getFecha_nac() + "¬" + user.getTfno() + "¬" + user.getImagen());
                        }
                        dataOut.flush();
                        break;

                    case "tarjeta":
                        texto = dataIn.readUTF();
                        //System.out.println(texto);
                        TarjetaBus bus = tsbdi.getTarjeta(Long.parseLong(texto));
                        dataOut.writeUTF(texto);
                        dataOut.flush();
                        dataOut.writeUTF(bus.getSaldo() + "/" + bus.getDescuento());
                        dataOut.flush();
                        break;

                    case "nombre_municipio":
                        id = dataIn.readInt();
                        String nombre = mdi.getNombreMunicipio(id);
                        dataOut.writeUTF(nombre);
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
                        double saldo = dataIn.readDouble();
                        //System.out.println(saldo);
                        texto = dataIn.readUTF();
                        //System.out.println(texto);
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
                        id = dataIn.readInt();
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

                    case "btarjetaCredito":
                        int pos = dataIn.readInt();
                        System.out.println(pos);
                        tcdi.borrar(pos);
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
                        dateFormat = new SimpleDateFormat("HH:mm:ss");
                        horaActual = new java.util.Date();
                        horaCodigo = dateFormat.format(horaActual);
                        qr = new CodigoQR(horaCodigo);
                        cdi = new CodigoDAOImp(con);
                        cdi.insertar(qr);
                        codigo = cdi.getCodigo(horaCodigo);
                        numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                        tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.1, codigo.getIdCodigo());
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
                            dateFormat = new SimpleDateFormat("HH:mm:ss");
                            horaActual = new java.util.Date();
                            horaCodigo = dateFormat.format(horaActual);
                            qr = new CodigoQR(horaCodigo);
                            cdi = new CodigoDAOImp(con);
                            cdi.insertar(qr);
                            codigo = cdi.getCodigo(horaCodigo);
                            numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                            tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.5, codigo.getIdCodigo());
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
                            dateFormat = new SimpleDateFormat("HH:mm:ss");
                            horaActual = new java.util.Date();
                            horaCodigo = dateFormat.format(horaActual);
                            qr = new CodigoQR(horaCodigo);
                            cdi = new CodigoDAOImp(con);
                            cdi.insertar(qr);
                            codigo = cdi.getCodigo(horaCodigo);
                            numAbsoluto = Math.abs(Long.parseLong(datos[0]));
                            tarjetaBus = new TarjetaBus(numAbsoluto, Integer.parseInt(datos[1]), 0, 0.3, codigo.getIdCodigo());
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

                    case "i_imagen":
                        texto = dataIn.readUTF();
                        System.out.println(texto);
                        datos = texto.split("¬");
                        udi.insertarImagen(datos[0], datos[1]);
                        if (udi.insertado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "iviaje":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        //DateFormat sdf = new SimpleDateFormat("hh:mm");
                        java.util.Date d = new java.util.Date();
                        java.sql.Date date = new java.sql.Date(d.getTime());
                        Viaje viaje = new Viaje(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]),
                                Integer.parseInt(datos[2]), Double.parseDouble(datos[3]), datos[4], datos[5], date);
                        System.out.println("viaje " + viaje);
                        vdi = new ViajeDAOImp(con);
                        viajes = vdi.getAllViajes();
                        boolean existente = false;
                        if (viajes.isEmpty()) {
                            vdi.insertarViaje(viaje);
                            //existente = false;
                            if (vdi.insertado) {
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                dataOut.writeUTF("incorrecto");
                                dataOut.flush();
                            }
                        } else {
                            for (int i = 0; i < viajes.size(); i++) {
                                if (viajes.get(i).getIdLinea() == viaje.getIdLinea() && viajes.get(i).getIdMunicipio()
                                        == viaje.getIdMunicipio() && compruebaHora(viajes.get(i).getHoraSalida(), viaje.getHoraSalida())) {
                                    existente = true;
                                    break;
                                }
                            }
                            if (existente) {
                                System.out.println("existente");
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                System.out.println("no existente");
                                vdi.insertarViaje(viaje);
                                existente = false;
                                if (vdi.insertado) {
                                    dataOut.writeUTF("correcto");
                                    dataOut.flush();
                                } else {
                                    dataOut.writeUTF("incorrecto");
                                    dataOut.flush();
                                }
                            }
                        }
                        break;

                    case "actualiza_codigo":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        id = tsbdi.getIdCodigo(Long.parseLong(datos[1]));
                        qr = new CodigoQR(id, datos[0], datos[2]);
                        cdi = new CodigoDAOImp(con);
                        cdi.updateHora(qr);
                        break;

                    case "actualiza_saldo":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        //if (Integer.parseInt(datos[2]) == cont) {
                        for (int i = 0; i < 1; i++) {
                            //double nuevoSaldo = Double.parseDouble(datos[0]) - (Double.parseDouble(datos[2]) * Double.parseDouble(datos[3]));
                            //System.out.println("Nuevo saldo: " + nuevoSaldo);
                            tsbdi.restaSaldo(Long.parseLong(datos[1]), /*nuevoSaldo*/ Double.parseDouble(datos[0]));
                            if (tsbdi.recarga) {
                                //cont++;
                                dataOut.writeUTF("correcto");
                                dataOut.flush();
                            } else {
                                dataOut.writeUTF("incorrecto");
                                dataOut.flush();
                            }
                        }
                        break;

                    case "codigo":
                        texto = dataIn.readUTF();
                        cdi = new CodigoDAOImp(con);
                        if (!cdi.getAllCodigos().isEmpty()) {
                            id = tsbdi.getIdCodigo(Long.parseLong(texto));
                            System.out.println(id);
                            CodigoQR qr = cdi.getCodigoById(id);
                            if (qr.getMensaje() == null) {
                                dataOut.writeUTF("Sin uso");
                            } else {
                                dataOut.writeUTF(qr.getMensaje());
                            }
                            dataOut.flush();
                        }
                        break;

                    case "direccion_parada":
                        texto = dataIn.readUTF();
                        System.out.println(texto);
                        String direccion = pdi.getDireccion(texto);
                        System.out.println(direccion);
                        dataOut.writeUTF(direccion);
                        dataOut.flush();
                        break;

                    case "refrescar":
                        Inserts inserts = new Inserts(con);
                        inserts.insertaRegistros();
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
        clientes = udi.getAllClientes();
        encriptar = new Encriptar();
        List<Cliente> admins = udi.getAllAdmins();
        String convertida = encriptar.encriptar(contraseña, CLAVE);
        if (cadena.contains("Admin")) {
            for (int i = 0; i < admins.size(); i++) {
                if (usuario.equals(admins.get(i).getNombre()) && convertida.equals(admins.get(i).getContraseña())
                        && admins.get(i).getIdCliente() == admins.get(i).getId()) {
                    correcto = true;
                    try {
                        dataOut.writeUTF("correcto");
                        dataOut.flush();
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            for (int j = 0; j < clientes.size(); j++) {
                if (usuario.equals(clientes.get(j).getNombre()) && convertida.equals(clientes.get(j).getContraseña())
                        && clientes.get(j).getIdCliente() == clientes.get(j).getId()) {
                    correcto = true;
                    try {
                        dataOut.writeUTF("correcto cliente");
                        dataOut.flush();
                        dataOut.writeInt(clientes.get(j).getIdCliente());
                        dataOut.flush();
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (!correcto) {
                clientes = udi.getAllRevisores();
                for (int j = 0; j < clientes.size(); j++) {
                    if (usuario.equals(clientes.get(j).getNombre()) && convertida.equals(clientes.get(j).getContraseña())
                            && clientes.get(j).getIdCliente() == clientes.get(j).getId()) {
                        correcto = true;
                        try {
                            dataOut.writeUTF("correcto revisor");
                            dataOut.flush();
                            dataOut.writeInt(clientes.get(j).getIdCliente());
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

    private boolean compruebaHora(String horaViaje, String nuevaHora) {
        boolean iguales = false;
        if (horaViaje.charAt(0) == nuevaHora.charAt(0) && horaViaje.charAt(1) == nuevaHora.charAt(1)
                && horaViaje.charAt(3) == nuevaHora.charAt(3) && horaViaje.charAt(4) == nuevaHora.charAt(4)) {
            iguales = true;
        }

        return iguales;
    }

    private void sendEmail(String correo, String usuario, String contraseña) {
        try {
            String email = "mvictoria.29397@gmail.com";
            String password = "29397Vicky";

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });
            //mailSession.setDebug(true);

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            message.setSubject("Usuario y contraseña");
            message.setContent("Usuario: " + usuario + "\n" + "Contraseña: " + contraseña, "text/html; charset=utf-8");

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(email, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        //} 
    }
}

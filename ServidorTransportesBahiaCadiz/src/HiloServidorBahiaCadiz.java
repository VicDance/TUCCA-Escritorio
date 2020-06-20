
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
import dao.LugarInteresDAOImp;
import dao.MunicipioDAOImp;
import dao.NucleoDAOImp;
import dao.ParadaDAOImp;
import dao.PuntoVentaDAOImp;
import dao.TarjetaCreditoDAOImp;
import dao.TarjetasBusDAOImp;
import dao.ViajeDAOImp;
import dao.ZonaDAOImp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.mail.Authenticator;
import serializable.Cliente;
import serializable.CodigoQR;
import serializable.Linea;
import serializable.LugarInteres;
import serializable.Municipio;
import serializable.Nucleo;
import serializable.Parada;
import serializable.PuntoVenta;
import serializable.TarjetaBus;
import serializable.TarjetaCredito;
import serializable.TarjetaEstandar;
import serializable.TarjetaEstudiante;
import serializable.TarjetaJubilado;
import serializable.Usuario;
import serializable.Viaje;
import serializable.Zona;
import scripts.Inserts;
import serializable.Cabecera;

/**
 *
 * @author Vicky
 */
public class HiloServidorBahiaCadiz extends Thread implements Clave {

    private final Socket cliente;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
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
    private List<Cabecera> cabeceras;
    private UsuarioDAOImp udi;
    private LineaDAOImp ldi;
    private ParadaDAOImp pdi;
    private MunicipioDAOImp mdi;
    private NucleoDAOImp ndi;
    private ZonaDAOImp zdi;
    private TarjetaCreditoDAOImp tcdi;
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
    private String encriptada;
    private int idUsuario;

    public HiloServidorBahiaCadiz(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(cliente.getOutputStream());
            inputStream = new ObjectInputStream(cliente.getInputStream());
            con = new Conector();
            con.connect();
            while (true) {
                System.out.println("servidor escuchando");
                //String cadena = dataIn.readUTF();
                String cadena = inputStream.readUTF();
                //System.out.println("cadena: " + cadena);
                switch (cadena) {
                    case "encriptar":
                        //lee si cliente, admin o revisor
                        texto = inputStream.readUTF();
                        System.out.println(texto);
                        usuario = (Usuario) inputStream.readObject();
                        encriptar = new Encriptar();
                        encriptada = encriptar.encriptar(usuario.getContraseña(), CLAVE);
                        udi = new UsuarioDAOImp(con);
                        if (texto.equalsIgnoreCase("cliente")) {
                            usuario.setContraseña(encriptada);
                            udi.insertar(usuario);
                            Usuario us = udi.getId(usuario.getNombre());
                            udi.insertarCliente(us.getId());
                        } else if (texto.equalsIgnoreCase("revisor")) {
                            sendEmail(usuario.getCorreo(), usuario.getNombre(), usuario.getContraseña());
                            usuario.setContraseña(encriptada);
                            udi.insertar(usuario);
                            Usuario us = udi.getId(usuario.getNombre());
                            udi.insertarRevisor(us.getId());
                            if (udi.insertado) {
                                envia("correcto");
                            } else {
                                envia("correcto");
                            }
                        } else {
                            usuario.setContraseña(encriptada);
                            udi.insertar(usuario);
                            Usuario us = udi.getId(usuario.getNombre());
                            udi.insertarAdmin(us.getId());
                            if(udi.insertado){
                                envia("correcto");
                            }else{
                                envia("incorrecto");
                            }
                        }
                        break;
                    case "inicio":
                        nombreUsuario = inputStream.readUTF();
                        contraseña = inputStream.readUTF();
                        comprobarCredenciales(cadena, nombreUsuario, contraseña);
                        break;

                    case "inicioAdmin":
                        nombreUsuario = inputStream.readUTF();
                        contraseña = inputStream.readUTF();
                        comprobarCredenciales(cadena, nombreUsuario, contraseña);
                        break;

                    case "usuarios":
                        udi = new UsuarioDAOImp(con);
                        usuarios = udi.getAll();
                        enviaInt(usuarios.size());
                        //System.out.println(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            enviaObject(usuario1);
                        }
                        break;

                    case "clientes":
                        udi = new UsuarioDAOImp(con);
                        clientes = udi.getAllClientes();
                        enviaInt(clientes.size());
                        for (Cliente cli : clientes) {
                            enviaObject(cli);
                        }
                        break;

                    case "revisores":
                        udi = new UsuarioDAOImp(con);
                        clientes = udi.getAllRevisores();
                        enviaInt(clientes.size());
                        //System.out.println(clientes.size());
                        for (Cliente cli : clientes) {
                            enviaObject(cli);
                        }
                        break;

                    case "lineas":
                        ldi = new LineaDAOImp(con);
                        lineas = ldi.getAllLineas();
                        enviaInt(lineas.size());
                        for (Linea linea : lineas) {
                            enviaObject(linea);
                        }
                        break;

                    case "paradas":
                        pdi = new ParadaDAOImp(con);
                        paradas = pdi.getAllParadas();
                        enviaInt(paradas.size());
                        for (Parada parada : paradas) {
                            enviaObject(parada);
                        }
                        break;
                        
                    case "cabeceras":
                        pdi = new ParadaDAOImp(con);
                        cabeceras = pdi.getAllCabecera();
                        enviaInt(cabeceras.size());
                        for (Cabecera parada : cabeceras) {
                            enviaObject(parada);
                        }
                        break;
                        
                    case "regulares":
                        pdi = new ParadaDAOImp(con);
                        cabeceras = pdi.getAllRegular();
                        enviaInt(cabeceras.size());
                        for (Cabecera parada : cabeceras) {
                            enviaObject(parada);
                        }
                        break;

                    case "paradas_viaje":
                        texto = inputStream.readUTF();
                        datos = texto.split("/");
                        pdi = new ParadaDAOImp(con);
                        ldi = new LineaDAOImp(con);
                        lineas = ldi.getLineasNucleo(Integer.parseInt(datos[1]), Integer.parseInt(datos[3]));
                        enviaInt(lineas.size());
                        //int[] stringParadas;
                        for (Linea linea : lineas) {
                            String l = ldi.getNombreLinea(linea.getIdLinea());
                            envia(l);
                            paradas = pdi.getParadasViaje(linea.getIdLinea(), Integer.parseInt(datos[1]), Integer.parseInt(datos[3]));
                            System.out.println(l);
                            enviaInt(paradas.size());
                            for (Parada parada : paradas) {
                                System.out.println(parada);
                                enviaObject(parada);
                            }
                        }
                        break;

                    case "municipios":
                        mdi = new MunicipioDAOImp(con);
                        municipios = mdi.getAllMunicipios();
                        enviaInt(municipios.size());
                        for (Municipio muni : municipios) {
                            enviaObject(muni);
                        }
                        break;

                    case "nucleos":
                        ndi = new NucleoDAOImp(con);
                        nucleos = ndi.getAllNucleos();
                        enviaInt(nucleos.size());
                        for (Nucleo nucleo : nucleos) {
                            enviaObject(nucleo);
                        }
                        break;

                    case "zonas":
                        zdi = new ZonaDAOImp(con);
                        zonas = zdi.getAll();
                        enviaInt(zonas.size());
                        for (Zona zona : zonas) {
                            enviaObject(zona);
                        }
                        break;

                    case "tarjetas":
                        idUsuario = inputStream.readInt();
                        System.out.println("id: " + idUsuario);
                        tcdi = new TarjetaCreditoDAOImp(con);
                        tarjetas = tcdi.getTarjetasUsuario(idUsuario);
                        System.out.println(tarjetas.size());
                        enviaInt(tarjetas.size());
                        for (TarjetaCredito tarjeta : tarjetas) {
                            enviaObject(tarjeta);
                        }
                        break;

                    case "viajes":
                        idUsuario = inputStream.readInt();
                        vdi = new ViajeDAOImp(con);
                        List<Viaje> viajes = vdi.getViajesId(idUsuario);
                        enviaInt(viajes.size());
                        ldi = new LineaDAOImp(con);
                        mdi = new MunicipioDAOImp(con);
                        for (int i = 0; i < viajes.size(); i++) {
                            String nombreLinea = ldi.getNombreLinea(viajes.get(i).getIdLinea());
                            String nombreMunicipio = mdi.getNombreMunicipio(viajes.get(i).getIdMunicipio());
                            envia(nombreLinea + "/" + nombreMunicipio + "/"
                                    + viajes.get(i).getHoraSalida() + "/" + viajes.get(i).getFechaViaje().getTime());
                        }
                        break;

                    case "tarjetasb":
                        idUsuario = inputStream.readInt();
                        tsbdi = new TarjetasBusDAOImp(con);
                        List<TarjetaBus> tarjetasBus = tsbdi.getTarjetasUsuario(idUsuario);
                        enviaInt(tarjetasBus.size());
                        estandares = tsbdi.getAllTarjetasEstandar();
                        estudiantes = tsbdi.getAllTarjetasEstudiante();
                        jubilados = tsbdi.getAllTarjetasJubilado();
                        for (int i = 0; i < tarjetasBus.size(); i++) {
                            if (tarjetasBus.get(i).getDescuento() == 0.1) {
                                //System.out.println("estandar");
                                for (TarjetaEstandar tarjeta : estandares) {
                                    if (tarjetasBus.get(i).getNumTarjeta() == tarjeta.getNumTarjeta()) {
                                        envia(tarjeta.getNumTarjeta() + "/" + tarjeta.getFecha_expedicion()
                                                + "/" + "estandar");
                                    }
                                }
                            } else if (tarjetasBus.get(i).getDescuento() == 0.3) {
                                //System.out.println("estundar");
                                for (TarjetaEstudiante tarjeta : estudiantes) {
                                    if (tarjetasBus.get(i).getNumTarjeta() == tarjeta.getNumTarjeta()) {
                                        envia(tarjeta.getNumTarjeta() + "/" + tarjeta.getFecha_ini()
                                                + "/" + tarjeta.getFecha_fin() + "/" + "estudiante");
                                    }
                                }
                            } else {
                                for (TarjetaJubilado tarjeta : jubilados) {
                                    if (tarjetasBus.get(i).getNumTarjeta() == tarjeta.getNumTarjeta()) {
                                        envia(tarjeta.getNumTarjeta() + "/" + tarjeta.getAñoValidez()
                                                + "/" + "jubilado");
                                    }
                                }
                            }
                        }
                        break;

                    case "puntos_venta":
                        ndi = new NucleoDAOImp(con);
                        nucleos = ndi.getAllNucleos();
                        //System.out.println(nucleos.size());
                        enviaInt(nucleos.size());
                        for (int i = 0; i < nucleos.size(); i++) {
                            enviaObject(nucleos.get(i));
                        }
                        break;

                    case "lugares_interes":
                        LugarInteresDAOImp ludi = new LugarInteresDAOImp(con);
                        List<LugarInteres> lugares = ludi.getAllLugares();
                        enviaInt(lugares.size());
                        for (int i = 0; i < lugares.size(); i++) {
                            enviaObject(lugares.get(i));
                        }
                        break;

                    case "usuario":
                        udi = new UsuarioDAOImp(con);
                        texto = inputStream.readUTF();
                        usuarios = udi.getUsuario(texto);
                        enviaInt(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            enviaObject(usuario1);
                        }
                        break;

                    case "linea":
                        ldi = new LineaDAOImp(con);
                        texto = inputStream.readUTF();
                        lineas = ldi.getLinea(texto);
                        enviaInt(lineas.size());
                        for (Linea linea : lineas) {
                            enviaObject(linea);
                        }
                        break;

                    case "id_linea":
                        texto = inputStream.readUTF();
                        ldi = new LineaDAOImp(con);
                        int idLinea = ldi.getIdLinea(texto);
                        enviaInt(idLinea);
                        break;

                    case "parada":
                        pdi = new ParadaDAOImp(con);
                        texto = inputStream.readUTF();
                        paradas = pdi.getParada(texto);
                        enviaInt(paradas.size());
                        for (Parada parada : paradas) {
                            enviaObject(parada);
                        }
                        break;

                    case "municipio":
                        mdi = new MunicipioDAOImp(con);
                        texto = inputStream.readUTF();
                        municipios = mdi.getMunicipio(texto);
                        enviaInt(municipios.size());
                        for (Municipio muni : municipios) {
                            enviaObject(muni);
                        }
                        break;

                    case "nucleo":
                        ndi = new NucleoDAOImp(con);
                        texto = inputStream.readUTF();
                        nucleos = ndi.getNucleo(texto);
                        enviaInt(nucleos.size());
                        for (Nucleo nucleo : nucleos) {
                            enviaObject(nucleo);
                        }
                        break;

                    case "zona":
                        zdi = new ZonaDAOImp(con);
                        texto = inputStream.readUTF();
                        zonas = zdi.getZona(texto);
                        enviaInt(zonas.size());
                        for (Zona zona : zonas) {
                            enviaObject(zona);
                        }
                        break;

                    case "cliente":
                        texto = inputStream.readUTF();
                        udi = new UsuarioDAOImp(con);
                        Usuario user = udi.getId(texto);
                        //System.out.println(user.getImagen());
                        if (user.getImagen() == null) {
                            user.setImagen(new byte[0]);
                        }
                        System.out.println(user.getImagen());
                        enviaObject(user);
                        break;

                    case "clientes_busqueda":
                        texto = inputStream.readUTF();
                        udi = new UsuarioDAOImp(con);
                        usuarios = udi.getCliente(texto);
                        enviaInt(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            enviaObject(usuario1);
                        }
                        break;

                    case "revisor":
                        texto = inputStream.readUTF();
                        udi = new UsuarioDAOImp(con);
                        usuarios = udi.getRevisor(texto);
                        enviaInt(usuarios.size());
                        for (Usuario usuario1 : usuarios) {
                            enviaObject(usuario1);
                        }
                        break;

                    case "tarjeta":
                        texto = inputStream.readUTF();
                        tsbdi = new TarjetasBusDAOImp(con);
                        TarjetaBus bus = tsbdi.getTarjeta(Long.parseLong(texto));
                        envia(texto);
                        System.out.println("Num tarjeta: " + texto);
                        envia(bus.getSaldo() + "/" + bus.getDescuento());
                        System.out.println("Saldo y descuento: " + bus.getSaldo() + "/" + bus.getDescuento());
                        break;

                    case "nombre_municipio":
                        id = inputStream.readInt();
                        mdi = new MunicipioDAOImp(con);
                        String nombre = mdi.getNombreMunicipio(id);
                        envia(nombre);
                        break;

                    case "puntos_venta_mapa":
                        int idNucleo = inputStream.readInt();
                        PuntoVentaDAOImp pvdi = new PuntoVentaDAOImp(con);
                        List<PuntoVenta> puntos = pvdi.getPuntosVentaNucleo(idNucleo);
                        enviaInt(puntos.size());
                        for (int i = 0; i < puntos.size(); i++) {
                            envia(puntos.get(i).getLatitud() + "/" + puntos.get(i).getLongitud());
                        }
                        break;

                    case "rtarjeta":
                        double saldo = inputStream.readDouble();
                        texto = inputStream.readUTF();
                        tsbdi = new TarjetasBusDAOImp(con);
                        tsbdi.recargarTarjeta(Long.parseLong(texto), saldo);
                        System.out.println(tsbdi.recarga);
                        if (tsbdi.recarga) {
                            envia("correcto");
                        } else {
                            envia("incorrecto");
                        }
                        break;

                    case "busuario":
                        id = inputStream.readInt();
                        udi = new UsuarioDAOImp(con);
                        udi.borrar(id);
                        if (udi.borrado) {
                            envia("correcto");
                        } else {
                            envia("incorrecto");
                        }
                        break;

                    case "btarjetaBus":
                        int posicion = inputStream.readInt();
                        tsbdi = new TarjetasBusDAOImp(con);
                        tsbdi.borrarTarjeta(posicion);
                        break;

                    case "btarjetaCredito":
                        int pos = inputStream.readInt();
                        System.out.println(pos);
                        tcdi = new TarjetaCreditoDAOImp(con);
                        tcdi.borrar(pos);
                        break;

                    case "ntarjeta":
                        texto = inputStream.readUTF();
                        datos = texto.split("-");
                        TarjetaCredito tarjeta = new TarjetaCredito(datos[0], Integer.parseInt(datos[1]),
                                datos[2], datos[3]);
                        tcdi = new TarjetaCreditoDAOImp(con);
                        tcdi.insertar(tarjeta);
                        //System.out.println(tcdi.insertado);
                        if (tcdi.insertado) {
                            System.out.println("entra");
                            envia("correcto");
                        } else {
                            envia("incorrecto");
                        }
                        break;

                    case "tarjeta_es":
                        texto = inputStream.readUTF();
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
                            envia("correcto");
                        } else {
                            envia("incorrecto");
                        }
                        break;

                    case "tarjeta_ju":
                        texto = inputStream.readUTF();
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
                                envia("correcto");
                            } else {
                                envia("incorrecto");
                            }
                        } else {
                            envia("invalido");
                        }
                        break;

                    case "tarjeta_estu":
                        texto = inputStream.readUTF();
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
                                envia("correcto");
                            } else {
                                envia("incorrecto");
                            }
                        } else {
                            envia("invalido");
                        }
                        break;

                    case "iviaje":
                        texto = inputStream.readUTF();
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
                            } else {
                                System.out.println("no existente");
                                vdi.insertarViaje(viaje);
                                existente = false;
                            }
                        }
                        break;

                    case "actualiza_codigo":
                        texto = inputStream.readUTF();
                        datos = texto.split("/");
                        tsbdi = new TarjetasBusDAOImp(con);
                        id = tsbdi.getIdCodigo(Long.parseLong(datos[1]));
                        qr = new CodigoQR(id, datos[0], datos[2]);
                        cdi = new CodigoDAOImp(con);
                        cdi.updateHora(qr);
                        break;

                    case "actualiza_saldo":
                        texto = inputStream.readUTF();
                        datos = texto.split("/");
                        tsbdi = new TarjetasBusDAOImp(con);
                        for (int i = 0; i < 1; i++) {
                            tsbdi.restaSaldo(Long.parseLong(datos[1]), Double.parseDouble(datos[0]));
                        }
                        break;

                    case "actualiza_usuario":
                        udi = new UsuarioDAOImp(con);
                        Usuario nuevoUsuario = (Usuario) inputStream.readObject();
                        encriptar = new Encriptar();
                        encriptada = encriptar.encriptar(nuevoUsuario.getContraseña(), CLAVE);
                        System.out.println(nuevoUsuario);
                        nuevoUsuario.setContraseña(encriptada);
                        udi.updateUsuario(nuevoUsuario);
                        break;

                    case "codigo":
                        texto = inputStream.readUTF();
                        cdi = new CodigoDAOImp(con);
                        if (!cdi.getAllCodigos().isEmpty()) {
                            tsbdi = new TarjetasBusDAOImp(con);
                            id = tsbdi.getIdCodigo(Long.parseLong(texto));
                            System.out.println(id);
                            CodigoQR qr = cdi.getCodigoById(id);
                            if (qr.getMensaje() == null) {
                                outputStream.writeUTF("Sin uso");
                            } else {
                                outputStream.writeUTF(qr.getMensaje());
                            }
                            outputStream.flush();
                        }
                        break;

                    case "direccion_parada":
                        texto = inputStream.readUTF();
                        System.out.println(texto);
                        pdi = new ParadaDAOImp(con);
                        String direccion = pdi.getDireccion(texto);
                        System.out.println("direccion: " + direccion);
                        outputStream.writeUTF(direccion);
                        outputStream.flush();
                        break;

                    case "refrescar":
                        Inserts inserts = new Inserts(con);
                        inserts.insertaRegistros();
                        while (true) {
                            if (Inserts.fin) {
                                outputStream.writeUTF("fin");
                                outputStream.flush();
                                break;
                            }
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
            //Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.disconect();
                cliente.close();
                outputStream.close();
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean comprobarCredenciales(String cadena, String usuario, String contraseña) {
        boolean correcto = false;
        udi = new UsuarioDAOImp(con);
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
                    envia("correcto");
                    break;
                }
            }
        } else {
            for (int j = 0; j < clientes.size(); j++) {
                if (usuario.equals(clientes.get(j).getNombre()) && convertida.equals(clientes.get(j).getContraseña())
                        && clientes.get(j).getIdCliente() == clientes.get(j).getId()) {
                    correcto = true;
                    envia("correcto cliente");
                    enviaInt(clientes.get(j).getIdCliente());
                    break;
                }
            }
            if (!correcto) {
                clientes = udi.getAllRevisores();
                for (int j = 0; j < clientes.size(); j++) {
                    if (usuario.equals(clientes.get(j).getNombre()) && convertida.equals(clientes.get(j).getContraseña())
                            && clientes.get(j).getIdCliente() == clientes.get(j).getId()) {
                        correcto = true;
                        envia("correcto revisor");
                        enviaInt(clientes.get(j).getIdCliente());
                        break;
                    }
                }
            }
        }
        if (!correcto) {
            envia("incorrecto");
        }
        return correcto;
    }

    private void envia(String mensaje) {
        try {
            outputStream.writeUTF(mensaje);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviaInt(int mensaje) {
        try {
            outputStream.writeInt(mensaje);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviaObject(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean compruebaFechaJubilado(int idCliente) {
        boolean correcto = false;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        java.util.Date d = new java.util.Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        calendar2.setTime(date2);
        int anioActual = calendar2.get(Calendar.YEAR);
        udi = new UsuarioDAOImp(con);
        List<Cliente> clientes = udi.getAllClientes();
        for (int i = 0; i < clientes.size(); i++) {
            calendar.setTime(clientes.get(i).getFecha_nac());
            int anioCliente = calendar.get(Calendar.YEAR);
            if ((anioActual - anioCliente >= 65) && idCliente == clientes.get(i).getId()) {
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
        udi = new UsuarioDAOImp(con);
        List<Cliente> clientes = udi.getAllClientes();
        for (int i = 0; i < clientes.size(); i++) {
            if ((mesActual >= 9 || mesActual <= 6) && idCliente == clientes.get(i).getId()) {
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
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
                @Override
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

            //JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}

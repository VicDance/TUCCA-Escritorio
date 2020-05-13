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
import dao.TarjetaCreditoDAOImp;
import dao.ZonaDAOImp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Linea;
import model.Municipio;
import model.Nucleo;
import model.Parada;
import model.TarjetaCredito;
import model.Usuario;
import model.Zona;

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
    private UsuarioDAOImp udi;
    private LineaDAOImp ldi;
    private ParadaDAOImp pdi;
    private MunicipioDAOImp mdi;
    private NucleoDAOImp ndi;
    private ZonaDAOImp zdi;
    private TarjetaCreditoDAOImp tcdi;
    private String texto;
    private String[] datos;
    private Usuario usuario;

    public HiloServidorBahiaCadiz(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            dataOut = new DataOutputStream(cliente.getOutputStream());
            dataIn = new DataInputStream(cliente.getInputStream());
            con = new Conector();
            while (true) {
                System.out.println("servidor escuchando");
                String cadena = dataIn.readUTF();
                System.out.println(cadena);
                udi = new UsuarioDAOImp();
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
                            ClienteDAOImp cdi = new ClienteDAOImp();
                            cdi.insertar(us.getId());
                            if (udi.insertado && cdi.insertado) {
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
                                ClienteDAOImp cdi = new ClienteDAOImp();
                                cdi.insertar(us.getId());
                                if (udi.insertado && cdi.insertado) {
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
                        String usuario = dataIn.readUTF();
                        String contraseña = dataIn.readUTF();
                        comprobarCredenciales(usuario, contraseña);
                        break;

                    case "usuarios":
                        dataOut.writeInt(usuarios.size());
                        dataOut.flush();
                        for (Usuario usuario1 : usuarios) {
                            dataOut.writeUTF(usuario1.getId() + "/" + usuario1.getNombre() + "/" + usuario1.getCorreo() + "/"
                                    + usuario1.getFecha_nac() + "/" + usuario1.getTfno());
                            dataOut.flush();
                        }
                        break;

                    case "lineas":
                        ldi = new LineaDAOImp();
                        lineas = ldi.getAllLineas();
                        dataOut.writeInt(lineas.size());
                        dataOut.flush();
                        for (Linea linea : lineas) {
                            dataOut.writeUTF(linea.getIdLinea() + "/" + linea.getNombreLinea());
                            dataOut.flush();
                        }
                        break;

                    case "paradas":
                        pdi = new ParadaDAOImp();
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

                    case "municipios":
                        mdi = new MunicipioDAOImp();
                        municipios = mdi.getAllMunicipios();
                        dataOut.writeInt(municipios.size());
                        dataOut.flush();
                        for (Municipio muni : municipios) {
                            dataOut.writeUTF(muni.getIdMunicipio() + "/" + muni.getNombreMunicipio());
                            dataOut.flush();
                        }
                        break;

                    case "nucleos":
                        ndi = new NucleoDAOImp();
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
                        zdi = new ZonaDAOImp();
                        zonas = zdi.getAll();
                        dataOut.writeInt(zonas.size());
                        dataOut.flush();
                        for (Zona zona : zonas) {
                            dataOut.writeUTF(zona.getIdZona() + "/" + zona.getNombreZona());
                            dataOut.flush();
                        }
                        break;

                    case "tarjetas":
                        System.out.println("entra");
                        tcdi = new TarjetaCreditoDAOImp();
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

                    case "ilinea":
                        texto = dataIn.readUTF();
                        ldi.insertar(texto);
                        if (ldi.insertado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "imunicipio":
                        texto = dataIn.readUTF();
                        mdi.insertar(texto);
                        if (mdi.insertado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "izona":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        System.out.println(datos[0] + " " + datos[1]);
                        zdi.insertar(datos[0], datos[1]);
                        if (zdi.insertado) {
                            dataOut.writeUTF("correcto");
                            dataOut.flush();
                        } else {
                            dataOut.writeUTF("incorrecto");
                            dataOut.flush();
                        }
                        break;

                    case "bzona":
                        texto = dataIn.readUTF();
                        datos = texto.split("/");
                        System.out.println(datos[0] + " " + datos[1]);
                        zdi.deleteId(new Zona(datos[0], datos[1]));
                        if (zdi.borrado) {
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
                        System.out.println(tcdi.insertado);
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
                cliente.close();
                dataIn.close();
                dataOut.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean comprobarCredenciales(String usuario, String contraseña) {
        boolean correcto = false;
        usuarios = udi.getAll();
        encriptar = new Encriptar();
        for (int i = 0; i < usuarios.size(); i++) {
            String convertida = encriptar.encriptar(contraseña, CLAVE);
            if (usuario.equals(usuarios.get(i).getNombre()) && convertida.equals(usuarios.get(i).getContraseña())) {
                correcto = true;
                try {
                    dataOut.writeUTF("correcto");
                    dataOut.flush();
                    dataOut.writeUTF(usuario + "/" + usuarios.get(i).getCorreo() + "/" + usuarios.get(i).getFecha_nac()
                            + "/" + usuarios.get(i).getTfno() + "/" + usuarios.get(i).getId());
                    break;
                } catch (IOException ex) {
                    Logger.getLogger(HiloServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
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
}

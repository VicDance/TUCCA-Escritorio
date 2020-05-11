/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class ServidorBahiaCadiz {

    private void listen() {
        Properties configuracion = new Properties();
        ServerSocket servidor = null;
        final int PUERTO; 
        try {
            configuracion.load(new FileInputStream("config.props"));
            PUERTO = Integer.valueOf(configuracion.getProperty("port"));
            servidor = new ServerSocket(PUERTO);
            System.out.println("Conectado al servidor: " + servidor);
        } catch (IOException ex) {
            System.out.println("IMPOSIBLE CONECTAR CON EL SERVIDOR " + servidor);
            System.exit(-1);
        }

        Socket cliente = null;

        System.out.println("Servidor prestando servicio...");
        try {
            while (true) {
                cliente = servidor.accept();
                System.out.println("Cliente conectado al servidor");
                HiloServidorBahiaCadiz hilo = new HiloServidorBahiaCadiz(cliente);
                hilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                servidor.close();
                cliente.close();
            } catch (IOException ex) {
                Logger.getLogger(ServidorBahiaCadiz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        new ServidorBahiaCadiz().listen();
    }

}

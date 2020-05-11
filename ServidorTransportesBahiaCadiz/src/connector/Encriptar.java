/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connector;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Vicky
 */
public class Encriptar {
    private SecretKeySpec crearClave(String clave) {
        SecretKeySpec secretKey = null;
        try {
            byte[] claveEncriptacion = clave.getBytes("UTF-8");

            MessageDigest sha = MessageDigest.getInstance("SHA-256");

            claveEncriptacion = sha.digest(claveEncriptacion);
            claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);

            secretKey = new SecretKeySpec(claveEncriptacion, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secretKey;
    }
    
    public String encriptar(String datos, String claveSecreta) {
        String encriptado = null;
        try {
            SecretKeySpec secretKey = this.crearClave(claveSecreta);
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] datosEncriptar = datos.getBytes("UTF-8");
            byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
            encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encriptado;
    }
    
    public String desencriptar(String datosEncriptados, String claveSecreta)  {
        String datos = null;
        try {
            SecretKeySpec secretKey = this.crearClave(claveSecreta);
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
            byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
            datos = new String(datosDesencriptados);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Encriptar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }
}

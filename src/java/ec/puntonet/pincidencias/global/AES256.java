/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.puntonet.pincidencias.global;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Francisco Daza
 */
public class AES256 {
     /**
     * Método para cifrar una cadena con AES256 utilizando la llave $KEYGENERAL
     *
     * @param value cadena a cifrar
     * @return cadena cifrada
     * @throws Exception
     */
    public static String cifrar(String value) throws Exception {
        
      String cifrado = null;
        try {
            Key key = generateKey();
            // Se obtiene un cifrador AES
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Se inicializa para encriptacion y se encripta el texto,
            // que debemos pasar como bytes.
            aes.init(Cipher.ENCRYPT_MODE, key);
            byte[] encriptado = aes.doFinal(value.getBytes());
            // encriptado para ver su resultado
            byte[] encoded = Base64.encodeBase64(encriptado);
            cifrado = new String(encoded);
        } catch (Exception ex) {
            Logger.getLogger(AES256.class.getName()).log(Level.ERROR, null, ex);
        }
        return cifrado;
    }

    /**
     * Método para cifrar una cadena con AES256 utilizando la llave $KEYGENERAL
     *
     * @param value cadena a cifrar
     * @return cadena cifrada
     * @throws Exception
     */
    public static String descifrar(String value) throws Exception {
         String decifrado = null;
        try {
            // Generamos una clave de 128 bits adecuada para AES
            Key key = generateKey();
            // Se obtiene un cifrador AES
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] decordedValue = Base64.decodeBase64(value);
            // Se iniciliza el cifrador para desencriptar, con la
            // misma clave y se desencripta
            aes.init(Cipher.DECRYPT_MODE, key);
            byte[] desencriptado = aes.doFinal(decordedValue);
            decifrado = new String(desencriptado);
        } catch (Exception ex) {
            Logger.getLogger(AES256.class.getName()).log(Level.ERROR, null, ex);
        }
        return decifrado;
    }

    /**
     * Método que genera la llaver en función de la cadena $KEYGENERAL
     *
     * @return Llave para cifrado / descifrado
     * @throws Exception
     */
    private static Key generateKey() throws Exception {
        // Generamos una clave de 128 bits adecuada para AES
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key key = keyGenerator.generateKey();
        // Alternativamente, una clave que queramos que tenga al menos 16 bytes
        // y nos quedamos con los bytes 0 a 15
        key = new SecretKeySpec("3jVZbgZGfgdZ3a31".getBytes(), 0, 16, "AES");
        return key;
    }

    public static void main(String[] args) throws Exception {
        String pass = AES256.cifrar("Abc181714");
        System.out.println(pass);
    }
}

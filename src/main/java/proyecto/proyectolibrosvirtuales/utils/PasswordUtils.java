/**
 * Clase utilitaria para operaciones relacionadas con contraseñas.
 */
package proyecto.proyectolibrosvirtuales.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase utilitaria que proporciona métodos para encriptar contraseñas.
 */
public class PasswordUtils {

    /**
     * Encripta una contraseña utilizando el algoritmo SHA-256.
     *
     * @param password La contraseña a encriptar.
     * @return La representación en formato hexadecimal de la contraseña encriptada.
     * @throws RuntimeException Si ocurre una excepción NoSuchAlgorithmException durante el proceso de encriptación.
     */
    public static String encriptar(String password) {
        try {
            // Obtiene una instancia del algoritmo de hash SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Actualiza el objeto MessageDigest con los bytes de la contraseña
            md.update(password.getBytes());

            // Obtiene el hash resultante como un array de bytes
            byte[] digest = md.digest();

            // Convierte el array de bytes a una representación hexadecimal y la convierte a minúsculas
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            // Lanza una excepción de tiempo de ejecución si no se encuentra el algoritmo de hash
            throw new RuntimeException(e);
        }
    }
}

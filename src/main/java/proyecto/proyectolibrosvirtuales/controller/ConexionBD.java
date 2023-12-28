package proyecto.proyectolibrosvirtuales.controller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {
    /**
     * URL de la base de datos MySQL.
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/proy_libreria";

    /**
     * Nombre de usuario para la conexión a la base de datos.
     */
    private static final String USER = "root";

    /**
     * Contraseña para la conexión a la base de datos.
     */
    private static final String PASS = "root";
    /**
     * Configuración de HikariCP para la gestión de conexiones.
     */
    private static HikariConfig config = new HikariConfig();
    /**
     * Fuente de datos HikariCP que administra el conjunto de conexiones.
     */
    private static HikariDataSource ds;
    /**
     * Bloque estático para la inicialización de la configuración y la fuente de datos HikariCP.
     */
    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(USER);
        config.setPassword(PASS);
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }
    /**
     * Constructor privado para evitar la instancia no deseada de la clase.
     */
    public ConexionBD() {}
    /**
     * Obtiene una conexión activa desde la fuente de datos HikariCP.
     *
     * @return Conexión activa a la base de datos.
     * @throws SQLException Si hay un error al obtener la conexión.
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    /**
     * Cierra la conexión especificada.
     *
     * @param connection Conexión a cerrar.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Verifica si un usuario ya existe en la base de datos.
     *
     * @param username Nombre de usuario a verificar.
     * @return true si el usuario existe, false de lo contrario.
     * @throws SQLException Si hay un error en la consulta SQL.
     */
    public static boolean usuarioExiste(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param username Nombre de usuario del nuevo usuario.
     * @param password Contraseña del nuevo usuario.
     * @throws SQLException Si hay un error en la consulta SQL.
     */
    public static void insertarUsuario(String username, String password) throws SQLException {
        String query = "INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }
    /**
     * Verifica la validez de las credenciales de usuario en la base de datos.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña de usuario.
     * @return true si las credenciales son válidas, false de lo contrario.
     * @throws SQLException Si hay un error en la consulta SQL.
     */
    public static boolean usuarioValido(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    /**
     * Verifica si un ID de libro ya existe en la base de datos.
     *
     * @param id ID de libro a verificar.
     * @return true si el ID existe, false de lo contrario.
     */
    public static boolean idExiste(int id) {
        String query = "SELECT COUNT(*) FROM Libros WHERE idLibro = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
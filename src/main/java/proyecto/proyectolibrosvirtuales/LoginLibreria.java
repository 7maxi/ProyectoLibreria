/**
 * Clase principal de la aplicación que inicia la interfaz gráfica de inicio de sesión.
 * Extiende la clase Application de JavaFX.
 */
package proyecto.proyectolibrosvirtuales;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase principal que inicia la aplicación JavaFX para la gestión de una librería virtual.
 */
public class LoginLibreria extends Application {
    // Almacena la referencia al escenario principal de la aplicación
    private static Stage primaryStage;

    /**
     * Método principal que inicia la aplicación JavaFX y carga la interfaz de inicio de sesión.
     *
     * @param stage El escenario principal de la aplicación.
     * @throws IOException Si hay un error al cargar la interfaz de inicio de sesión desde el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la interfaz de inicio de sesión desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(LoginLibreria.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Establece el enfoque en la escena
        Platform.runLater(() -> {
            scene.getRoot().requestFocus();
        });

        // Configura el escenario principal y lo muestra
        stage.setTitle("Libreria");
        stage.setScene(scene);
        stage.show();

        // Almacena la referencia al escenario principal
        LoginLibreria.primaryStage = stage;
    }

    /**
     * Método estático que carga una nueva ventana y cierra la ventana principal.
     *
     * @param clase          La clase que llama a la carga de la ventana.
     * @param rutafxml       La ruta al archivo FXML de la nueva ventana.
     * @param tituloVentana  El título de la nueva ventana.
     * @throws IOException   Si hay un error al cargar la nueva ventana desde el archivo FXML.
     */
    public static void cargarVentana(Class<?> clase, String rutafxml, String tituloVentana) throws IOException {
        // Carga la nueva ventana desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(clase.getResource(rutafxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Configura el escenario de la nueva ventana y la muestra
        Stage stage = new Stage();
        stage.setTitle(tituloVentana);
        stage.setScene(scene);
        stage.show();

        // Cierra la ventana principal
        primaryStage.close();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch();
    }
}

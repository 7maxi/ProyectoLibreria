/**
 * Controlador para la interfaz de inicio de sesión y registro en la aplicación de la librería.
 * Maneja la lógica de interacción con el usuario, la validación de datos y las transiciones entre las vistas.
 */
package proyecto.proyectolibrosvirtuales.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import proyecto.proyectolibrosvirtuales.*;
import proyecto.proyectolibrosvirtuales.utils.Alertas;
import proyecto.proyectolibrosvirtuales.utils.Animaciones;
import proyecto.proyectolibrosvirtuales.utils.LibreriaUtils;
import proyecto.proyectolibrosvirtuales.utils.PasswordUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Controlador para la interfaz de inicio de sesión y registro.
 */
public class LoginController {
    @FXML
    Text animatedText;
    @FXML
    ImageView imgreload;
    @FXML
    ImageView cuadradoImgArriba;
    @FXML
    ImageView cuadradoImgAbajo;
    @FXML
    Button btnIniSes;
    @FXML
    Button btnConfirmar;
    @FXML
    Button btnRegistro;
    @FXML
    PasswordField inputConfPass;
    @FXML
    PasswordField inputPass;
    @FXML
    TextField inputUsuario;

    Animaciones controller = new Animaciones();
    LibreriaUtils libreriaUtils = new LibreriaUtils();
    Alertas alertas = new Alertas();

    /**
     * Inicializa el controlador y realiza las configuraciones iniciales.
     * Configura animaciones, borra errores y muestra un mensaje de bienvenida.
     */
    @FXML
    public void initialize() {
        borrarErrores();
        controller.animacionTexto(animatedText, "Bienvenido a la librería", 48);
    }
    /**
     * Borra los errores de los campos de entrada al escribir.
     */
    private void borrarErrores() {
        inputPass.setOnKeyTyped(event ->libreriaUtils.borrarErrorTextField(inputPass, inputConfPass));
        inputConfPass.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(inputPass, inputConfPass));
        inputUsuario.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(inputUsuario));
    }
    /**
     * Verifica si todos los campos de registro están completos.
     *
     * @return true si todos los campos están completos, false de lo contrario.
     */
    private boolean camposEstanCompletos() {
        return !inputUsuario.getText().isEmpty() && !inputPass.getText().isEmpty() && !inputConfPass.getText().isEmpty();
    }
    /**
     * Verifica si las contraseñas ingresadas coinciden.
     *
     * @return true si las contraseñas coinciden, false de lo contrario.
     */
    private boolean contrasenasCoinciden() {
        boolean coinciden = inputPass.getText().equals(inputConfPass.getText());
        if (!coinciden) {
            libreriaUtils.addError(inputConfPass, inputPass);
        }
        return coinciden;
    }
    /**
     * Maneja el evento de registro. Cambia la interfaz a la creación de usuario.
     */
    @FXML
    void registrarse(ActionEvent event) {
        libreriaUtils.borrarErrorTextField(inputPass, inputConfPass, inputUsuario) ;
        controller.animacionTexto(animatedText, "Creación de usuario", 48);
        controller.cambioLogRegis(btnIniSes, btnRegistro,inputConfPass, btnConfirmar);
        libreriaUtils.limpiarCampos(inputPass, inputConfPass, inputUsuario);
    }
    /**
     * Maneja el evento de confirmación de registro.
     * Valida los campos y agrega un nuevo usuario a la base de datos si los datos son válidos.
     */
   @FXML
void confirmar(ActionEvent event){
    if (!camposEstanCompletos()) {
        alertas.mostrarAlertaInfo("Debes completar todos los campos");
        return;
    }

    if (!contrasenasCoinciden()) {
        alertas.mostrarAlertaInfo("Las contraseñas no coinciden");
        return;
    }
    String username = inputUsuario.getText();
    String password = PasswordUtils.encriptar(inputPass.getText());
    try {
        Connection connection = ConexionBD.getConnection();
        if (!ConexionBD.usuarioExiste(username)) {
            ConexionBD.insertarUsuario(username, password);
            controller.cambioRegistroLogin(btnIniSes, btnRegistro,inputConfPass, btnConfirmar);
            controller.animacionTexto(animatedText, "El usuario ha sido creado con exito", 40);
            libreriaUtils.limpiarCampos(inputPass, inputConfPass, inputUsuario);
        } else {
            alertas.mostrarAlertaError("El usuario ya existe");
            libreriaUtils.addError(inputUsuario);
        }
        ConexionBD.closeConnection(connection);
    } catch (SQLException e) {
        e.printStackTrace();
        alertas.mostrarAlertaError("Error al conectar con la base de datos");
    }
}
    /**
     * Inicia sesión con las credenciales proporcionadas. Carga la interfaz principal si las credenciales son válidas.
     */
    @FXML
    private void iniciarSesion(){
        String username = inputUsuario.getText();
        String password = PasswordUtils.encriptar(inputPass.getText());
        try {
            Connection connection = ConexionBD.getConnection();
            if (!ConexionBD.usuarioValido(username, password)) {
                alertas.mostrarAlertaError("Usuario o contraseña incorrectos");
                inputUsuario.getStyleClass().add("input-error");
                inputPass.getStyleClass().add("input-error");
            } else {
                LoginLibreria.cargarVentana(LoginController.class,"/proyecto/proyectolibrosvirtuales/libreria-view.fxml", "Libreria");
            }
            ConexionBD.closeConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            alertas.mostrarAlertaError("Error al conectar con la base de datos");
        }
    }
}
package proyecto.proyectolibrosvirtuales.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Clase Alertas para mostrar diferentes tipos de alertas al usuario.
 */
public class Alertas {
    /**
     * Muestra una alerta de error con un mensaje específico.
     *
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    public void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de información con un mensaje específico.
     *
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    public void mostrarAlertaInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación con un mensaje específico.
     *
     * @param accion La acción que se va a confirmar.
     * @param idLibro El ID del libro relacionado con la acción.
     * @param tituloLibro El título del libro relacionado con la acción.
     * @return Un Optional que contiene el tipo de botón que el usuario presionó en respuesta a la alerta.
     */
    public Optional<ButtonType> alertaConfirmar(String accion, String idLibro, String tituloLibro) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres "  + accion + " el libro con ID: " + idLibro + " y título: " + tituloLibro + "?");
        return alert.showAndWait();
    }
}
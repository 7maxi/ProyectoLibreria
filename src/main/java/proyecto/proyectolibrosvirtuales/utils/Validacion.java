package proyecto.proyectolibrosvirtuales.utils;

import javafx.scene.control.TextField;

/**
 * Clase Validacion que proporciona métodos de utilidad para la validación de campos de texto en la interfaz de usuario.
 */
public class Validacion {

    /**
     * Valida uno o más campos de texto para asegurarse de que no estén vacíos.
     * Si un campo de texto está vacío, se añade la clase de estilo 'input-error'.
     * Si un campo de texto no está vacío, se elimina la clase de estilo 'input-error'.
     *
     * @param textFields Los campos de texto a validar.
     * @return true si todos los campos de texto son válidos, false si al menos uno de los campos de texto está vacío.
     */
    public static boolean validarCampos(TextField... textFields) {
        boolean validacion = true;
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                validacion = false;
                if (!textField.getStyleClass().contains("input-error")) {
                    textField.getStyleClass().add("input-error");
                }
            } else {
                textField.getStyleClass().remove("input-error");
            }
        }
        return validacion;
    }
}
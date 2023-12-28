package proyecto.proyectolibrosvirtuales.utils;

import javafx.scene.control.TextField;

/**
 * Clase LibreriaUtils que proporciona métodos de utilidad para la manipulación de campos de texto en la interfaz de usuario.
 */
public class LibreriaUtils{

    /**
     * Limpia el contenido de uno o más campos de texto.
     *
     * @param campos Los campos de texto a limpiar.
     */
    public void limpiarCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.setText("");
        }
    }

    /**
     * Elimina la clase de estilo 'input-error' de uno o más campos de texto.
     *
     * @param fields Los campos de texto a los que se les eliminará la clase de estilo.
     */
    public void borrarErrorTextField(TextField... fields) {
        for (TextField field : fields) {
            field.getStyleClass().remove("input-error");
        }
    }

    /**
     * Añade la clase de estilo 'input-error' a uno o más campos de texto.
     *
     * @param fields Los campos de texto a los que se les añadirá la clase de estilo.
     */
    public void addError(TextField... fields) {
        for (TextField field : fields) {
            field.getStyleClass().add("input-error");
        }
    }
}
package proyecto.proyectolibrosvirtuales.utils;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 * Clase Animaciones para crear diferentes tipos de animaciones en la interfaz de usuario.
 */
public class Animaciones {
    /**
     * Crea una animación de texto con un efecto de máquina de escribir.
     *
     * @param animatedText El texto que se va a animar.
     * @param texto El contenido del texto.
     * @param fontSize El tamaño de la fuente del texto.
     */
    public void animacionTexto(Text animatedText, String texto, int fontSize) {
        animatedText.setStyle("-fx-font-size: " + fontSize + "px;");
        final StringBuilder sb = new StringBuilder();
        Timeline timeline = new Timeline();
        for (int i = 0; i < texto.length(); i++) {
            final int finalI = i;
            KeyFrame kf = new KeyFrame(Duration.millis(90 * (i + 1)), event -> {
                sb.append(texto.charAt(finalI));
                animatedText.setText(sb.toString());
            });
            timeline.getKeyFrames().add(kf);
        }
        timeline.play();
    }
    /**
     * Crea una animación para cambiar de la vista de inicio de sesión a la vista de registro.
     *
     * @param iniSes El botón de inicio de sesión.
     * @param registro El botón de registro.
     * @param inputConfPass El campo de confirmación de contraseña.
     * @param btnConfirmar El botón de confirmación.
     */
    public void cambioLogRegis(Button iniSes, Button registro, PasswordField inputConfPass, Button btnConfirmar) {
        int iniSesTranslation = -200;
        int regTranslation = -300;
        int inputTranslation = -150;
        int btnConfirmarTranslation = -150;

        TranslateTransition translateIniSes = new TranslateTransition(Duration.seconds(0.3), iniSes);
        TranslateTransition translateReg = new TranslateTransition(Duration.seconds(0.3), registro);
        TranslateTransition translateInput = new TranslateTransition(Duration.seconds(0.3), inputConfPass);
        TranslateTransition translateConfirmar = new TranslateTransition(Duration.seconds(0.3), btnConfirmar);

        translateIniSes.setByX(iniSesTranslation);
        translateReg.setByX(regTranslation);
        translateInput.setByY(inputTranslation);
        translateConfirmar.setByY(btnConfirmarTranslation);

        translateIniSes.setAutoReverse(true);
        translateReg.setAutoReverse(true);
        translateInput.setAutoReverse(true);
        translateConfirmar.setAutoReverse(true);

        SequentialTransition sequentialTransition = new SequentialTransition(translateIniSes, translateReg, translateInput, translateConfirmar);

        Platform.runLater(sequentialTransition::play);

    }
    /**
     * Crea una animación para cambiar de la vista de registro a la vista de inicio de sesión.
     *
     * @param iniSes El botón de inicio de sesión.
     * @param registro El botón de registro.
     * @param inputConfPass El campo de confirmación de contraseña.
     * @param btnConfirmar El botón de confirmación.
     */
    public void cambioRegistroLogin(Button iniSes, Button registro, PasswordField inputConfPass, Button btnConfirmar) {
        int iniSesTranslation = 200;
        int regTranslation = 300;
        int inputTranslation = 150;
        int btnConfirmarTranslation = 150;

        TranslateTransition translateIniSes = new TranslateTransition(Duration.seconds(0.3), iniSes);
        TranslateTransition translateReg = new TranslateTransition(Duration.seconds(0.3), registro);
        TranslateTransition translateInput = new TranslateTransition(Duration.seconds(0.3), inputConfPass);
        TranslateTransition translateConfirmar = new TranslateTransition(Duration.seconds(0.3), btnConfirmar);

        translateIniSes.setByX(iniSesTranslation);
        translateReg.setByX(regTranslation);
        translateInput.setByY(inputTranslation);
        translateConfirmar.setByY(btnConfirmarTranslation);

        translateIniSes.setAutoReverse(true);
        translateReg.setAutoReverse(true);
        translateInput.setAutoReverse(true);
        translateConfirmar.setAutoReverse(true);

        SequentialTransition sequentialTransition = new SequentialTransition(translateConfirmar, translateInput, translateReg, translateIniSes);

        Platform.runLater(sequentialTransition::play);
    }

}

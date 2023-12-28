/**
 * @author Máximo
 * @version 1.0
 * Controlador para la interfaz de la librería en JavaFX.
 * Maneja la lógica de interacción con el usuario y la gestión de libros en una base de datos.
 */
package proyecto.proyectolibrosvirtuales.controller;
// Importaciones de clases necesarias de JavaFX y otras utilidades
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import proyecto.proyectolibrosvirtuales.*;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import proyecto.proyectolibrosvirtuales.utils.Alertas;
import proyecto.proyectolibrosvirtuales.utils.Animaciones;
import proyecto.proyectolibrosvirtuales.utils.LibreriaUtils;
import proyecto.proyectolibrosvirtuales.utils.Validacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import static proyecto.proyectolibrosvirtuales.controller.ConexionBD.getConnection;
/**
 * Clase que representa el controlador para la interfaz de la librería.
 */
public class LibreriaController {
    // Definición de los elementos de la interfaz gráfica
    @FXML
    Text tituloText;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnMod;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtAutor;
    @FXML
    protected TextField txtAño;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private TableColumn<Libros, String> colAutor;
    @FXML
    private TableColumn<Libros, String> colAño;
    @FXML
    private TableColumn<Libros, Integer> colCantidad;
    @FXML
    private TableColumn<Libros, Integer> colId;
    @FXML
    private TableColumn<Libros, String> colTitulo;
    @FXML
    private TableView<Libros> tablaLibros;
    // Instancias de clases de utilidades
    Alertas alertas = new Alertas();
    Animaciones animar = new Animaciones();
    LibreriaUtils libreriaUtils = new LibreriaUtils();

    /**
     * Inicializa el controlador y la interfaz gráfica.
     */
   @FXML
    void initialize(){
    // Asocia las columnas de la tabla con las propiedades de la clase Libros
       colId.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
       colTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloLibro"));
       colAutor.setCellValueFactory(new PropertyValueFactory<>("autorLibro"));
       colAño.setCellValueFactory(new PropertyValueFactory<>("añoLibro"));
       colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadLibro"));

       cargarLibros(); // Llama al método cargarLibros para llenar la tabla con los datos de los libros

       // Inicia una animación con el texto "Bienvenido a la librería" y una fuente de 48
       animar.animacionTexto(tituloText, "Bienvenido a la librería", 48);

       // Agrega un listener a la propiedad selectedItem de la tabla. Cuando se selecciona un nuevo libro, se llama al método seleccionarLibros
       tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           if (newSelection != null) {
               seleccionarLibros();
           }
       });

    tablaLibros.setFixedCellSize(30); // Establece el tamaño de las celdas de la tabla a 30
    controlarCampos(); // Llama al método controlarCampos para agregar validaciones a los campos de entrada
}

    /**
     * Agrega validaciones a los campos de entrada para garantizar la entrada de datos correcta.
     */
    public void controlarCampos() {
        txtAño.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 4) {
                txtAño.setText(oldValue);
            } else if (!newValue.matches("\\d*")) {
                txtAño.setText(newValue.replaceAll("\\D", ""));
            }
        });
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("\\D", ""));
            }
        });
        txtId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtId.setText(newValue.replaceAll("\\D", ""));
            }
        });
        txtAutor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtAutor.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        txtId.setOnKeyTyped(event ->libreriaUtils.borrarErrorTextField(txtId));
        txtAutor.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(txtAutor));
        txtTitulo.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(txtTitulo));
        txtAutor.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(txtAutor));
        txtAño.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(txtAño));
        txtCantidad.setOnKeyTyped(event -> libreriaUtils.borrarErrorTextField(txtCantidad));
    }

    /**
     * Método llamado cuando se selecciona un libro en la tabla.
     * Actualiza los campos de entrada con los detalles del libro seleccionado.
     */
    private void seleccionarLibros() {
        Libros libros = tablaLibros.getSelectionModel().getSelectedItem();
        txtId.setText(String.valueOf(libros.getIdLibro()));
        txtTitulo.setText(libros.getTituloLibro());
        txtAutor.setText(libros.getAutorLibro());
        txtAño.setText(libros.getAñoLibro());
        txtCantidad.setText(String.valueOf(libros.getCantidadLibro()));

    }

    private void agregarLibrosAResultado(ResultSet resultSet) throws SQLException {
        // Limpiar la tabla antes de cargar nuevos datos
        tablaLibros.getItems().clear();

        // Procesar los resultados
        while (resultSet.next()) {
            int idLibro = resultSet.getInt("idLibro");
            String titulo = resultSet.getString("Titulo");
            String autor = resultSet.getString("Autor");
            String año = resultSet.getString("aniopublicacion");
            int cantidad = resultSet.getInt("cantidaddisponible");
            tablaLibros.getItems().add(new Libros(idLibro, titulo, autor, año, cantidad));
        }
    }
    /**
     * Ejecuta una consulta SQL que modifica la base de datos.
     *
     * @param query     Consulta SQL a ejecutar.
     * @param titulo    Título del libro.
     * @param autor     Autor del libro.
     * @param año       Año de publicación del libro.
     * @param cantidad  Cantidad disponible del libro.
     * @param id        ID del libro.
     * @throws SQLException Si hay un error al ejecutar la consulta.
     */
    private void ejecutarConsulta(String query, String titulo, String autor, String año, int cantidad, int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, titulo);
            preparedStatement.setString(2, autor);
            preparedStatement.setString(3, año);
            preparedStatement.setInt(4, cantidad);
            preparedStatement.setInt(5, id);

            preparedStatement.executeUpdate();
            cargarLibros();
            libreriaUtils.limpiarCampos(txtId, txtTitulo, txtAutor, txtAño, txtCantidad);
            ConexionBD.closeConnection(connection);
        }
    }
    /**
     * Actualiza la tabla de libros con los datos de la base de datos.
     */
    @FXML
    private void refreshV2(){
        System.out.println("Actualizando tabla...");
        cargarLibros();
    }

    /**
     * Carga los libros desde la base de datos y los muestra en la tabla.
     */
    private void cargarLibros() {
        String query = "SELECT idLibro, titulo, idlibro, titulo, autor, aniopublicacion, cantidaddisponible FROM Libros";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             agregarLibrosAResultado(resultSet);
             ConexionBD.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Busca libros en la base de datos según el título o la ID proporcionada.
     */
    @FXML
    private void buscarLibros() {
        String query = "SELECT idLibro, titulo, idlibro, titulo, autor, aniopublicacion, cantidaddisponible" +
                " FROM Libros WHERE titulo LIKE ? OR idLIbro = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Configurar los parámetros de la consulta
            preparedStatement.setString(1, "%" + txtBusqueda.getText() + "%");
            try {
                int id = Integer.parseInt(txtBusqueda.getText());
                preparedStatement.setInt(2, id);
            } catch (NumberFormatException e) {
                preparedStatement.setInt(2, -1);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            // Limpiar la tabla antes de cargar nuevos datos
            agregarLibrosAResultado(resultSet);
            ConexionBD.closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("No se han podido cargar los clientes de la Base de Datos");
        }
    }
    /**
     * Agrega un nuevo libro a la base de datos.
     * Si la ID ya existe, muestra un mensaje de error.
     * Si la ID no existe, agrega el libro a la base de datos y muestra un mensaje de éxito.
     */
    @FXML
    private void addLibro(){
        if (!Validacion.validarCampos(txtId, txtTitulo, txtAutor, txtAño, txtCantidad)) {
            alertas.mostrarAlertaInfo("Todos los campos deben estar llenos");
            return;
        }
        String query = "INSERT INTO Libros (titulo, autor, aniopublicacion, cantidaddisponible, idLibro) VALUES (?,?,?,?,?)";
        try{
            int id = Integer.parseInt(txtId.getText());
            if (!ConexionBD.idExiste(id)) {
                ejecutarConsulta(query, txtTitulo.getText(), txtAutor.getText(), txtAño.getText(),
                        Integer.parseInt(txtCantidad.getText()), id);
                animar.animacionTexto(tituloText, "Libro agregado con éxito", 40);
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> animar.animacionTexto(tituloText, "Bienvenido a la librería", 48));
                pause.play();            } else {
                alertas.mostrarAlertaError("La ID ya existe en la base de datos");            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Borra el libro seleccionado de la base de datos.
     * Si el usuario confirma la acción, se borra el libro.
     */
    @FXML
    private void borrarLibro(){
       String id = txtId.getText();
       String titulo = txtTitulo.getText();
       Optional<ButtonType> result = alertas.alertaConfirmar("borrar",id, titulo);
       if (result.isPresent() && result.get() == ButtonType.OK) {
            String query = "DELETE FROM Libros WHERE idLibro = ?";
            try{
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(txtId.getText()));
                preparedStatement.executeUpdate();
                cargarLibros();
                libreriaUtils.limpiarCampos(txtId, txtTitulo, txtAutor, txtAño, txtCantidad);
                ConexionBD.closeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Modifica el libro seleccionado en la base de datos.
     * Si el usuario confirma la acción, se modifica el libro.
     */
    @FXML
    private void modLibro(){
        if (!Validacion.validarCampos(txtId, txtTitulo, txtAutor, txtAño, txtCantidad)) {
            alertas.mostrarAlertaInfo("Todos los campos deben estar llenos");
            return;
        }
        String id = txtId.getText();
        String titulo = txtTitulo.getText();
        Optional<ButtonType> result = alertas.alertaConfirmar("modificar",id, titulo);
        if (result.isPresent() && result.get() == ButtonType.OK) {
        String query = "UPDATE Libros SET titulo = ?, autor = ?, aniopublicacion = ?, cantidaddisponible = ? WHERE idLibro = ?";
        try{
            ejecutarConsulta(query, txtTitulo.getText(), txtAutor.getText(), txtAño.getText(),
                    Integer.parseInt(txtCantidad.getText()), Integer.parseInt(txtId.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

}

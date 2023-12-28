package proyecto.proyectolibrosvirtuales;

/**
 * Clase que representa un libro en la aplicación de la librería virtual.
 * Contiene información sobre el identificador del libro, título, autor, año de publicación y cantidad disponible.
 */
public class Libros {
    /**
     * Identificador único del libro.
     */
    Integer idLibro;

    /**
     * Título del libro.
     */
    String tituloLibro;

    /**
     * Autor del libro.
     */
    String autorLibro;

    /**
     * Año de publicación del libro.
     */
    String añoLibro;

    /**
     * Cantidad disponible de copias del libro.
     */
    Integer cantidadLibro;

    /**
     * Constructor que inicializa un objeto Libros con la información proporcionada.
     *
     * @param id Identificador único del libro.
     * @param titulo Título del libro.
     * @param autor Autor del libro.
     * @param año Año de publicación del libro.
     * @param cantidad Cantidad disponible de copias del libro.
     */
    public Libros(int id, String titulo, String autor, String año, int cantidad) {
        this.idLibro = id;
        this.tituloLibro = titulo;
        this.autorLibro = autor;
        this.añoLibro = año;
        this.cantidadLibro = cantidad;
    }

    /**
     * Obtiene el identificador del libro.
     *
     * @return Identificador del libro.
     */
    public Integer getIdLibro() {
        return idLibro;
    }

    /**
     * Establece el identificador del libro.
     *
     * @param idLibro Nuevo identificador del libro.
     */
    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return Título del libro.
     */
    public String getTituloLibro() {
        return tituloLibro;
    }

    /**
     * Establece el título del libro.
     *
     * @param tituloLibro Nuevo título del libro.
     */
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return Autor del libro.
     */
    public String getAutorLibro() {
        return autorLibro;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autorLibro Nuevo autor del libro.
     */
    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }

    /**
     * Obtiene el año de publicación del libro.
     *
     * @return Año de publicación del libro.
     */
    public String getAñoLibro() {
        return añoLibro;
    }

    /**
     * Establece el año de publicación del libro.
     *
     * @param añoLibro Nuevo año de publicación del libro.
     */
    public void setAñoLibro(String añoLibro) {
        this.añoLibro = añoLibro;
    }

    /**
     * Obtiene la cantidad disponible de copias del libro.
     *
     * @return Cantidad disponible de copias del libro.
     */
    public Integer getCantidadLibro() {
        return cantidadLibro;
    }

    /**
     * Establece la cantidad disponible de copias del libro.
     *
     * @param cantidadLibro Nueva cantidad disponible de copias del libro.
     */
    public void setCantidadLibro(Integer cantidadLibro) {
        this.cantidadLibro = cantidadLibro;
    }
}

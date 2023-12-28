module proyecto.proyectolibrosvirtuales {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires java.xml.bind;

    opens proyecto.proyectolibrosvirtuales to javafx.fxml;
    exports proyecto.proyectolibrosvirtuales;
    exports proyecto.proyectolibrosvirtuales.controller;
    opens proyecto.proyectolibrosvirtuales.controller to javafx.fxml;
    exports proyecto.proyectolibrosvirtuales.utils;
    opens proyecto.proyectolibrosvirtuales.utils to javafx.fxml;
}
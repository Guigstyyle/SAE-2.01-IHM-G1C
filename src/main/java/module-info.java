module com.example.sae201 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires com.gluonhq.maps;

    opens com.example.sae201 to javafx.fxml;
    exports com.example.sae201;
}
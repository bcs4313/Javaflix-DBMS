module com.core.javaflix {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jsch;

    opens com.core.javaflix to javafx.fxml;
    exports com.core.javaflix;
}
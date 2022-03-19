module com.core.javaflix {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jsch;

    opens com.core.javaflix to javafx.fxml;
    exports com.core.javaflix.registration_windows;
    opens com.core.javaflix.registration_windows to javafx.fxml;
    exports com.core.javaflix.utilities;
    opens com.core.javaflix.utilities to javafx.fxml;
    exports com.core.javaflix.collection_windows;
    opens com.core.javaflix.collection_windows to javafx.fxml;
    exports com.core.javaflix.profile_windows;
    opens com.core.javaflix.profile_windows to javafx.fxml;
    exports com.core.javaflix.social_windows;
    opens com.core.javaflix.social_windows to javafx.fxml;
    exports com.core.javaflix.dashboard_windows;
    opens com.core.javaflix.dashboard_windows to javafx.fxml;
}
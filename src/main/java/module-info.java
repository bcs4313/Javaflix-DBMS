module com.core.javaflix {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires jsch;

    opens com.core.javaflix to javafx.fxml;
    exports com.core.javaflix;
    exports com.core.javaflix.utilities;
    opens com.core.javaflix.utilities to javafx.fxml;
    exports com.core.javaflix.dashboard;
    opens com.core.javaflix.dashboard to javafx.fxml;
    exports com.core.javaflix.dashboard.collections;
    opens com.core.javaflix.dashboard.collections to javafx.fxml;
    exports com.core.javaflix.dashboard.friends.friending;
    opens com.core.javaflix.dashboard.friends.friending to javafx.fxml;
    exports com.core.javaflix.dashboard.settings;
    opens com.core.javaflix.dashboard.settings to javafx.fxml;
    exports com.core.javaflix.dashboard.subwindows;
    opens com.core.javaflix.dashboard.subwindows to javafx.fxml;
    exports com.core.javaflix.dashboard.friends.user_search;
    opens com.core.javaflix.dashboard.friends.user_search to javafx.fxml;
    exports com.core.javaflix.dashboard.friends.following;
    opens com.core.javaflix.dashboard.friends.following to javafx.fxml;
}
package com.core.javaflix.dashboard;

import com.core.javaflix.BaseApplication;
import com.core.javaflix.utilities.AbstractWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class DashboardWindow extends AbstractWindow {
    /**
     * load this window into the main stage (manually called)
     * @throws IOException thrown if fxml file isn't retrieved properly
     */
    @Override
    public void load() throws IOException {
        // load application page
        FXMLLoader fxmlLoader = new FXMLLoader(BaseApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 852, 480); // standard 480p window size
        BaseApplication.window.setScene(scene);
        BaseApplication.window.show();
    }
}

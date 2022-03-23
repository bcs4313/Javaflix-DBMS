package com.core.javaflix.utilities;

import java.io.IOException;

public abstract class AbstractWindow {
    /**
     * load this window into the main stage (manually called)
     * @throws IOException thrown if fxml file isn't retrieved properly
     */
    public void load() throws IOException {}
}

package com.core.javaflix.utilities;

import com.core.javaflix.BaseApplication;

import java.io.IOException;

public class AbstractWindow {
    /**
     * load this window into the main stage (manually called)
     * @throws IOException thrown if fxml file isn't retrieved properly
     */
    public void load() throws IOException {}

    public static void loadLastPage() {
        AbstractWindow lastPage = BaseApplication.storage.pageStorage.get(BaseApplication.storage.pageStorage.size()-1);
        BaseApplication.storage.pageStorage.remove(lastPage);
        try {
            lastPage.load();
        } catch (Exception e) {
            System.out.println("Failed to load last page");
        }
    }
}

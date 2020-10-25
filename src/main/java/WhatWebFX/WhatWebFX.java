/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package WhatWebFX;

import WhatWebFX.controllers.ui.MainKudeatzaile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WhatWebFX extends Application {
    private Parent mainUI;

    private Stage stage;
    private Scene sceneM;

    private MainKudeatzaile mainKud;


    public static void main(String[] args) { }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pantailakKargatu();

        stage.setTitle("WhatWebFX");
        stage.setScene(sceneM);
        stage.show();
    }

    private void pantailakKargatu() throws IOException {
        FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/Main.fxml"));
        mainUI = (Parent) loaderMain.load();
        mainKud= loaderMain.getController();
        mainKud.setMainApp(this);
        sceneM = new Scene(mainUI);
    }
}

package ehu.isad.controllers.ui;
import javafx.application.Platform;
import javafx.scene.control.Button;

import ehu.isad.WhatWebFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class WhatWebKudeatzaile implements Initializable {


    private WhatWebFX mainApp;

    public void setMainApp(WhatWebFX main){
        this.mainApp = main;
    }

    @FXML
    private Button btn_whatweb;


    @FXML
    void onClickClose(ActionEvent event) {
        Stage stage=mainApp.getStage();
        stage.close();
    }

    @FXML
    void onClickCMS(ActionEvent event) {
        this.mainApp.CMS();
    }

    @FXML
    void onClickServer(ActionEvent event) {

    }

    @FXML
    void onClickScan(ActionEvent event) {

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void botoiaFocus(){
        Platform.runLater(() -> btn_whatweb.requestFocus());
    }
}

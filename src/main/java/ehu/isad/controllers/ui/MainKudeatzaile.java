package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKudeatzaile implements Initializable {

    private WhatWebFX mainApp;

    //Pantaila mugitzeko kalkulurako
    private double xOffset = 0;
    private double yOffset = 0;


    public void setMainApp(WhatWebFX main){
        this.mainApp = main;
    }

    @FXML
    void onClickAddURL(ActionEvent event) {

    }

    @FXML
    void onClickClose(ActionEvent event) {
        Stage stage=mainApp.getStage();
        stage.close();
    }

    @FXML
    void onClickServer(ActionEvent event) {
    }

    @FXML
    void onClickWhatWeb(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        Stage stage=mainApp.getStage();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }




}

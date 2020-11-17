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

    private MainKudeatzaile mainKudeatzaile;

    public void setMainKudeatzaile(MainKudeatzaile mainKudeatzaile) {
        this.mainKudeatzaile = mainKudeatzaile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import ehu.isad.model.MongoErabiltzailea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

public class MainKudeatzaile {

    private WhatWebFX mainApp;

    public MainKudeatzaile(WhatWebFX mainApp) {
        this.mainApp = mainApp;
    }

    public void setMainApp(WhatWebFX main){
        this.mainApp = main;
    }

    @FXML
    private Button btnCMS;

    @FXML
    private Button btnServer;

    @FXML
    private Button btnWhatWeb;

    @FXML
    private AnchorPane anchorCMSSQL;

    @FXML
    private AnchorPane anchorCMSMongo;

    @FXML
    private AnchorPane anchorWhatWebo;

    @FXML
    private AnchorPane anchorServer;

    @FXML
    private CMSMongoKudeatzaile cmsMongoController;

    @FXML
    private CMSKudeatzaile cmsSQLController ;

    @FXML
    private ServerKudeatzaile serverController ;

    @FXML
    private WhatWebKudeatzaile whatWebController ;

    @FXML
    void onClick(ActionEvent event) {
        if(event.getSource()==btnCMS){
            MongoErabiltzailea erabiltzailea=MongoErabiltzailea.getInstance();
            if (!erabiltzailea.getCollection().equals("")) {
                anchorCMSMongo.toFront();
                cmsMongoController.hasieratu();
            }
            else{
                anchorCMSSQL.toFront();
                cmsSQLController.taulaEguneratu();
            }
        }
        else if (event.getSource()==btnWhatWeb){
            anchorWhatWebo.toFront();
        }
        else {
            anchorServer.toFront();
            serverController.hasieratu();
        }
    }

    @FXML
    void onClickClose(ActionEvent event) {
        mainApp.getStage().close();
        System.exit(0);
    }

    @FXML
    void onClickMin(ActionEvent event) {
        mainApp.getStage().setIconified(true);
    }


    public void hasieratu(){
        anchorCMSSQL.toFront();
    }

}
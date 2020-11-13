package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKudeatzaile implements Initializable {

    private WhatWebFX mainApp;

    public void setMainApp(WhatWebFX main){
        this.mainApp = main;
    }

    @FXML
    private Label lbl_whatwebfx;

    @FXML
    private Button btn_cms;


    @FXML
    private Button btn_server;

    @FXML
    private Button btn_whatWeb;


    @FXML
    private TableColumn<?, ?> clmn_url;

    @FXML
    private TableColumn<?, ?> clmn_cms;

    @FXML
    private TableColumn<?, ?> clmn_version;

    @FXML
    private TableColumn<?, ?> clmn_azkenEguneraketa;

    @FXML
    private Label lbl_deskrib;

    @FXML
    private Label lbl_cmsGoian;

    @FXML
    private Pane tbl_taula;

    @FXML
    private TextField txt_bilatu;

    @FXML
    private ComboBox<?> cmbx_non;

    @FXML
    private Button btn_addurl;

    @FXML
    private Button btn_itxi;

    @FXML
    void onClickAddURL(ActionEvent event) {

    }

    @FXML
    void onClickClose(ActionEvent event) {
        Stage stage=mainApp.getStage();
        stage.close();
    }

    @FXML
    void onClickCMS(ActionEvent event) {

    }

    @FXML
    void onClickServer(ActionEvent event) {
    }

    @FXML
    void onClickWhatWeb(ActionEvent event) {
        this.mainApp.WhatWeb();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}

package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import ehu.isad.model.CmsSQL;
import ehu.isad.model.Herrialdea;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CMSMongoKudeatzaile implements Initializable {

    @FXML
    private TableView<CmsSQL> table;

    @FXML
    private TableColumn<CmsSQL, Hyperlink> colURL;

    @FXML
    private TableColumn<CmsSQL, String> colCMS;

    @FXML
    private TableColumn<CmsSQL, String> colVersion;

    @FXML
    private TableColumn<CmsSQL, String> colLastUpdated;

    public CMSMongoKudeatzaile(){
    }


    public void taulaEguneratu(){

    }

    @FXML
    void onClickAddURL(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setPlaceholder(new Label("Ez dago emaitzik"));

    }
}

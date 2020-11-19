package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.WhatWebKud;
import ehu.isad.model.Cms;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class CMSKudeatzaile implements Initializable {

    private List<Cms> cmsList;


    @FXML
    private TextField txt_bilatu;

    @FXML
    private TableView<Cms> tbl_cms;

    @FXML
    private TableColumn<Cms, String> clmn_url;

    @FXML
    private TableColumn<Cms, String> clmn_cms;

    @FXML
    private TableColumn<Cms, String> clmn_version;

    @FXML
    private TableColumn<Cms, String> clmn_lastupdate;

    @FXML
    void onClickAddURL(ActionEvent event) {
        this.datuaKargatu();
    }

    public CMSKudeatzaile() {
        System.out.println("CMS kud instantzia");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nola bistaratu gelaxkak (zutabearen arabera)
        // Get value from property of UserAccount.
        tbl_cms.setEditable(true);
        clmn_cms.setCellValueFactory(new PropertyValueFactory<>("cms"));
        clmn_lastupdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        clmn_url.setCellValueFactory(new PropertyValueFactory<>("url"));
        clmn_version.setCellValueFactory(new PropertyValueFactory<>("version"));

        //add your data to the table here.
        datuaKargatu();
    }

    public void datuaKargatu(){
        cmsList = CmsKud.getInstantzia().lortuCmsak();
        ObservableList<Cms> cmsak = FXCollections.observableArrayList(cmsList);
        tbl_cms.setItems(cmsak);
    }
}

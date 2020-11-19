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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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
        cmsList = CmsKud.getInstantzia().lortuCmsak();
        this.datuaKargatu(cmsList);
    }

    public CMSKudeatzaile() {
        System.out.println("CMS kud instantzia");
    }

    @FXML
    void onTestuaAldatuDa(KeyEvent event) {
        this.bilaketak(txt_bilatu.getText());
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
        cmsList = CmsKud.getInstantzia().lortuCmsak();
        datuaKargatu(cmsList);
    }

    public void datuaKargatu(List<Cms> cmsLista){
        ObservableList<Cms> cmsak = FXCollections.observableArrayList(cmsLista);
        tbl_cms.setItems(cmsak);
    }

    private void bilaketak(String testua){
        List<Cms> cmsListLag = new ArrayList<Cms>();
        for(int i=0; i < cmsList.size(); i++){
            if(cmsList.get(i).getUrl().contains(testua)){
                cmsListLag.add(cmsList.get(i));
            }
        }
        this.datuaKargatu(cmsListLag);
    }
}

package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.ServerKud;
import ehu.isad.controllers.db.WhatWebKud;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ServerKudeatzaile implements Initializable {

    @FXML
    private TableView<String> tblServer;

    @FXML
    private TableColumn<String, String> zutabeServer;

    public void hasieratu(){
        var emaitza=kargatuLista();

        zutabeServer.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue()));

        tblServer.setItems(emaitza);

    }

    private ObservableList<String> kargatuLista(){
        ServerKud serverKud=ServerKud.getInstantzia();
        List<String> targets = serverKud.lortuTargets();
        ObservableList<String> lista= FXCollections.observableArrayList(targets);
        return lista;
    }

    @FXML
    void onClickEguneratu(ActionEvent event) {
        hasieratu();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hasieratu();
    }
}

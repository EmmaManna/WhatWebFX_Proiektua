package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.ServerKud;
import ehu.isad.controllers.db.WhatWebKud;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerKudeatzaile {

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
        ObservableList<String> lista= FXCollections.observableArrayList();

        ServerKud serverKud=ServerKud.getInstantzia();
        ResultSet resultSet=serverKud.lortuTargets();

        try{
            while (resultSet.next()){
                lista.add(resultSet.getString("target"));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    @FXML
    void onClickEguneratu(ActionEvent event) {
        hasieratu();
    }



}

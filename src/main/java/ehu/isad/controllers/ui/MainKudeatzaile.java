package ehu.isad.controllers.ui;

import ehu.isad.WhatWebFX;
import javafx.application.Platform;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainKudeatzaile implements Initializable {

    private WhatWebFX mainApp;

    public void setMainApp(WhatWebFX main){
        this.mainApp = main;
    }

    @FXML
    private Button btn_cms;

    @FXML
    private TextField txt_bilatu;


    @FXML
    void onClickAddURL(ActionEvent event) {
        this.botoiaFocus();
        this.urlIrakurri(txt_bilatu.getText());
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

    public void botoiaFocus(){
        Platform.runLater(() -> btn_cms.requestFocus());
    }

    public List<String> urlIrakurri(String url) {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p = null;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                p = Runtime.getRuntime().exec("wsl whatweb --colour='never' " + url);
            } else {
                p = Runtime.getRuntime().exec("whatweb  --colour='never' " + url);
            }
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
                System.out.println(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processes;
    }
}

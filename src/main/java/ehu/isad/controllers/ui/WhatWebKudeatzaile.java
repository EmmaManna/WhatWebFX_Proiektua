package ehu.isad.controllers.ui;
import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.WhatWebKud;
import ehu.isad.model.Bilaketa;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class WhatWebKudeatzaile implements Initializable {

    private MainKudeatzaile mainKudeatzaile;

    public void setMainKudeatzaile(MainKudeatzaile mainKudeatzaile) {
        this.mainKudeatzaile = mainKudeatzaile;
    }

    @FXML
    private TextField txt_url;

    @FXML
    private Button btn_scan;

    @FXML
    private TextArea txt_log;

    @FXML
    private ImageView mgvw_loading;


    public TextField getTxt_url() {
        return txt_url;
    }

    public void setTxt_url(TextField txt_url) {
        this.txt_url = txt_url;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public WhatWebKudeatzaile() {
        System.out.println("WhatWeb kud instantzia");
    }

    @FXML
    void onClickScan(ActionEvent event) {
        if(!txt_url.getText().equals("")){
            txt_log.setText("");
            if(WhatWebKud.getInstantzia().jadaBilatuta(txt_url.getText())){
                txt_log.setText("Jada ditugu datuak");
                txt_url.setText("");
            }
            else{
                //Irudi kargatzen
                Image i = new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"LOADING.gif").toURI().toString());
                mgvw_loading.setImage(i);
                mgvw_loading.setVisible(true);
                txt_log.setWrapText(true);

                Thread taskThread=hasieratuThread();

                taskThread.start();
            }
        }
        else{
            txt_log.setText("URL bat idatzi mesedez.");
        }
    }


    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.getKeyCode("Enter"))){
            onClickScan(new ActionEvent());
        }
    }

    public Thread hasieratuThread(){

        Thread taskThread = new Thread( () -> {

            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            Bilaketa bilaketa=new Bilaketa();

            bilaketa.urlIrakurri(txt_url.getText()).forEach(line ->  {
                emaitza.append( line + newLine );
            });

            try {
                WhatWebKud.getInstantzia().insertIrakurri();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Platform.runLater( () -> {
                if(emaitza.toString().equals("")){
                    txt_log.setText("Bilatutako URL-a ez da existitzen. Bilatu beste bat mesedez.");
                }
                else{
                    txt_log.setText(emaitza.toString());
                }
                mgvw_loading.setVisible(false);
                txt_url.setText("");

            } );

        });
        return taskThread;
    }


}

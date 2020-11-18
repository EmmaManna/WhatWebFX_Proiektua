package ehu.isad.controllers.ui;
import ehu.isad.controllers.db.WhatWebKud;
import javafx.application.Platform;
import javafx.scene.control.Button;

import ehu.isad.WhatWebFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onClickScan(ActionEvent event) {
        if(WhatWebKud.getInstantzia().jadaBilatuta(txt_url.getText())){
            System.out.println("Jada ditugu datuak");
            txt_url.setText("");
        }
        else{
            //Irudi de homer pentsatzen (monito con pandereta)
            String path = System.getProperty("user.dir")+
                    System.getProperty("file.separator")+
                    "src"+
                    System.getProperty("file.separator")+
                    "main"+
                    System.getProperty("file.separator")+
                    "resources"+
                    System.getProperty("file.separator")+
                    "Images"+
                    System.getProperty("file.separator")+
                    "tenor.gif";
            Image i = new Image(new File(path).toURI().toString());
            mgvw_loading.setImage(i);

            Thread taskThread = new Thread( () -> {

                String newLine = System.getProperty("line.separator");
                final StringBuilder emaitza = new StringBuilder();
                urlIrakurri(txt_url.getText()).forEach(line ->  {
                    emaitza.append( line + newLine );
                });

                try {
                    WhatWebKud.getInstantzia().insertIrakurri();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.runLater( () -> {
                    //txt_bilatu.setText(emaitza.toString());
                   txt_log.setText(emaitza.toString());
                    mgvw_loading.setVisible(false);
                    txt_url.setText("");
                } );

            });

            taskThread.start();
        }
    }

    public List<String> urlIrakurri(String url) {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p=null;
            String komandoa = "whatweb --colour='never' --log-sql="+System.getProperty("user.dir")+System.getProperty("file.separator")+"insertak.sql " + url;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                komandoa = "wsl " + komandoa;
            }
            else komandoa="/bin/"+komandoa;

            p = Runtime.getRuntime().exec(komandoa);

            System.out.println(p.getOutputStream());

            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                processes.add(line);
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }

        return processes;
    }

}

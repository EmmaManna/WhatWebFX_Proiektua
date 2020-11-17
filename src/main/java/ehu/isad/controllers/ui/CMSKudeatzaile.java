package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.WhatWebKud;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class CMSKudeatzaile {

    @FXML
    private TextField txt_bilatu;

    @FXML
    private ImageView mgvw_monito;

    @FXML
    void onClickAddURL(ActionEvent event) {
        if(WhatWebKud.getInstantzia().jadaBilatuta(txt_bilatu.getText())){
            System.out.println("Jada ditugu datuak");
            txt_bilatu.setText("");
        }
        else{
            //Idatzi Kargatzen bla bla bla
            txt_bilatu.setText("Kargatzen...");
            //Irudi de homer pentsatzen (monito con pandereta)
            String path = System.getProperty("user.dir")+
                    System.getProperty("file.separator")+
                    "resources"+
                    System.getProperty("file.separator")+
                    "Images"+
                    System.getProperty("file.separator")+
                    "tenor.gif";
            Image i = new Image(new File(path).toURI().toString());
            mgvw_monito.setImage(i);

            Thread taskThread = new Thread( () -> {

                String newLine = System.getProperty("line.separator");
                final StringBuilder emaitza = new StringBuilder();
                urlIrakurri(txt_bilatu.getText()).forEach(line ->  {
                    emaitza.append( line + newLine );
                });

                try {
                    WhatWebKud.getInstantzia().insertIrakurri();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.runLater( () -> {
                    //txt_bilatu.setText(emaitza.toString());
                    System.out.println(emaitza.toString());
                    mgvw_monito.setVisible(false);
                    txt_bilatu.setText("");
                } );

            });

            taskThread.start();
        }
    }

    public List<String> urlIrakurri(String url) {
        List<String> processes = new LinkedList<String>();
        try {
            String line;
            Process p = null;
            String komandoa = "whatweb --colour='never' --log-sql=insertak.sql " + url;
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                komandoa = "wsl " + komandoa;
            }
            p = Runtime.getRuntime().exec(komandoa);

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

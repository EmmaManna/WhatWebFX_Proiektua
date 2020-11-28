package ehu.isad.controllers.ui;
import ehu.isad.controllers.db.WhatWebSQLKud;
import ehu.isad.model.MongoErabiltzailea;
import ehu.isad.utils.Bilaketa;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtCollection;

    @FXML
    private CheckBox checkBoxIkusi;

    @FXML
    private Pane lblAktibatuta;

    @FXML
    private TextField txtPassIkusgarri;

    public WhatWebKudeatzaile() { }


    public TextField getTxt_url() {
        return txt_url;
    }

    public void setTxt_url(TextField txt_url) {
        this.txt_url = txt_url;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblAktibatuta.setVisible(false);
        txt_log.setEditable(false);
        txtPass.toFront();
    }


    @FXML
    void onKlikEgin(MouseEvent event) {
        txt_log.setText("");
        txt_url.setText("");
    }

    @FXML
    void onClickScan(ActionEvent event) {
        if(!txt_url.getText().equals("")){
            txt_log.setText("");
            if(WhatWebSQLKud.getInstantzia().jadaBilatuta(txt_url.getText())){
                txt_log.setText("Jada ditugu datuak");
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


    @FXML
    void onCommit(ActionEvent event) {
        if(!txtCollection.getText().isBlank()){
            MongoErabiltzailea erabiltzailea=MongoErabiltzailea.getInstance();

            erabiltzailea.setCollection(txtCollection.getText());
            erabiltzailea.setIzena(txtUser.getText());
            erabiltzailea.setPasahitza(txtPass.getText());
            lblAktibatuta.setVisible(true);
        }
    }


    @FXML
    void onClickCheckBox(ActionEvent event) {
        if (checkBoxIkusi.isSelected()){
            txtPassIkusgarri.setText(txtPass.getText());
            txtPass.setVisible(false);
        }
        else {
            txtPass.setText(txtPassIkusgarri.getText());
            txtPass.setVisible(true);
        }
    }


    @FXML
    void onClickLogOut(ActionEvent event) {
        MongoErabiltzailea.getInstance().setPasahitza("");
        MongoErabiltzailea.getInstance().setIzena("");
        MongoErabiltzailea.getInstance().setCollection("");

        txtPass.setText("");
        txtPassIkusgarri.setText("");
        txtCollection.setText("");
        txtUser.setText("");
        lblAktibatuta.setVisible(false);
    }


    public Thread hasieratuThread(){

        Thread taskThread = new Thread( () -> {

            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            Bilaketa bilaketa=new Bilaketa();

            bilaketa.urlIrakurri(txt_url.getText()).forEach(line ->  {
                emaitza.append( line + newLine );
            });

            //mongo ez badu erabiltzen sartu datu basean
            if (MongoErabiltzailea.getInstance().getCollection().equals("")){
                try {
                    WhatWebSQLKud.getInstantzia().insertIrakurri();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Platform.runLater( () -> {
                if(emaitza.toString().equals("")){
                    txt_log.setText("Bilatutako URL-a ez da existitzen. Bilatu beste bat mesedez.");
                }
                else{
                    txt_log.setText(emaitza.toString());
                }
                mgvw_loading.setVisible(false);

            } );

        });
        return taskThread;
    }

}

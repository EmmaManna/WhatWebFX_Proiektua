package ehu.isad.controllers.ui;
import ehu.isad.controllers.db.CmsMongoKud;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblAktibatuta.setVisible(false);
        txt_log.setEditable(false);
        txtPass.toFront();
    }


    @FXML
    void onKlikEgin(MouseEvent event) {
        //URL-a sartzeko testu kutxan klik egitean guztia ezabatzen da
        txt_log.setText("");
        txt_url.setText("");
    }


    @FXML
    void onClickScan(ActionEvent event) {
        //Eskaneatzeko botoia sakatzen bada, WhatWeb-i dei egiten zaio datuak lortzeko eta kargatzeko
        //Emaitza testu kutxan agertuko da.
        //Ez bada URL-rik idazten edo datuak jada gordeta badaude mezu bat panatailaratuko da
        if(!txt_url.getText().equals("")){
            txt_log.setText("");
            if(MongoErabiltzailea.getInstance().getCollection().equals("")){
                if(WhatWebSQLKud.getInstantzia().jadaBilatuta(txt_url.getText())){
                    txt_log.setText("Jada ditugu datuak");
                }
                else{
                    HasieratuWWKomandoa();
                }
            }
            else{
                txt_log.setText("");
                if(CmsMongoKud.getInstance().bilatuMongo(txt_url.getText())){
                    txt_log.setText("Jada ditugu datuak");
                }
                else{
                    HasieratuWWKomandoa();
                }

            }
        }
        else{
            txt_log.setText("URL bat idatzi mesedez.");
        }
    }

    private void HasieratuWWKomandoa() {
        Image i = new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"LOADING.gif").toURI().toString());
        mgvw_loading.setImage(i);
        mgvw_loading.setVisible(true);
        txt_log.setWrapText(true);

        Thread taskThread=hasieratuThread();
        taskThread.start();
    }


    @FXML
    void onKeyPressed(KeyEvent event) {
        //Enter tekla sakatzen bada eskaneoa hasieratuko da
        if (event.getCode().equals(KeyCode.getKeyCode("Enter"))){
            onClickScan(new ActionEvent());
        }
    }


    @FXML
    void EnterSakatuCommit(KeyEvent event) {
        //TODO: No sé qué hace JonQ
        if (event.getCode().equals(KeyCode.getKeyCode("Enter"))){
            onCommit(new ActionEvent());
        }
    }


    @FXML
    void onCommit(ActionEvent event) {
        //TODO: No sé qué hace JonQ
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
        //Pasahitza sartzen denenean ikustea edo ez ikustea kudeatzen du MongoDB kautotzean
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
        //Logout botoian klik egitean MongoDB erabiltzailea hasieratzen da
        // eta MongoDB desaktibatzen da
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
        //WhatWeb exekutatzen da Thread baten barruan aplikazioa ez blokeatzeko
        //Datuak dagokion datu basean gordetzen dira eta emaitza testu kutxan agertzen da
        //Bilatutako URL-a ez bada existitzen mezu bat pantailaratuko da.
        Thread taskThread = new Thread( () -> {
            String newLine = System.getProperty("line.separator");
            final StringBuilder emaitza = new StringBuilder();
            Bilaketa bilaketa=new Bilaketa();

            bilaketa.urlIrakurri(txt_url.getText()).forEach(line ->  {
                emaitza.append( line + newLine );
            });

            //Mongo ez badu erabiltzen sartu datu basean
            if (MongoErabiltzailea.getInstance().getCollection().equals("")){
                DatuakSartuSQL();
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


    private void DatuakSartuSQL(){
        //Eskaneoan lortzen diren datuak SQLiten gordetzen dira
        try {
            WhatWebSQLKud.getInstantzia().insertIrakurri();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

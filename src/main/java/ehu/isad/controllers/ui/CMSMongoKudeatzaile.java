package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.CmsMongoKud;
import ehu.isad.controllers.db.WhatWebMongoKud;
import ehu.isad.model.Cms;
import ehu.isad.model.CmsMongo;
import ehu.isad.model.Herrialdea;
import ehu.isad.model.MongoErabiltzailea;
import ehu.isad.utils.Bilaketa;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CMSMongoKudeatzaile {

    private List<CmsMongo> cmsMongoList;
    private List<CmsMongo> cmsMongoListGuztiak;

    @FXML
    private ComboBox<Herrialdea> cmbx_herrialdeak;

    @FXML
    private ImageView imgLoadin;

    @FXML
    private TextField txt_bilatu;

    @FXML
    private TableView<CmsMongo> tbl_cms;

    @FXML
    private TableColumn<CmsMongo, Hyperlink> clmn_url;

    @FXML
    private TableColumn<CmsMongo, String> clmn_cms;

    @FXML
    private TableColumn<CmsMongo, String> clmn_version;

    public void hasieratu(){
        clmn_url.setCellValueFactory(new PropertyValueFactory<>("target"));
//        clmn_url.setCellFactory(param -> new TableCell<>(){
//            public void updateItem(){
//                final Hyperlink hyperlink=new Hyperlink(param.getText());
//            }
//        });

        clmn_cms.setCellValueFactory(new PropertyValueFactory<>("plugins"));


        //Taula hutsa dagoenean agertzen den mezua
        tbl_cms.setPlaceholder(new Label("Ez dago emaitzik"));

        //Adding action to the choice box
        cmbx_herrialdeak.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    this.iragazkia(cmbx_herrialdeak.getItems().get(new_val.intValue()));
                });


    }

    @FXML
    void onClickAddURL(ActionEvent event) {
        String mongoEgoera= MongoErabiltzailea.getInstance().getCollection();
        if (!txt_bilatu.getText().equals("") && !mongoEgoera.equals("")){
            Boolean emaitza= WhatWebMongoKud.getInstance().jadaBilatutaMongo(txt_bilatu.getText());
            if (!emaitza){
                bilatuMongo();
            }
            else {
                txt_bilatu.setText("");
                System.out.println("jada URL bilatu duzu");
            }
        }
        else txt_bilatu.setText("URL bat sartu mesedez");
    }

    @FXML
    void onKlikEgin(MouseEvent event) {
        txt_bilatu.setText("");
    }

    @FXML
    void onTestuaAldatuDa(KeyEvent event) {
        this.bilaketak(txt_bilatu.getText());
    }

    private void bilaketak(String testua){
        List<CmsMongo> cmsListLag = new ArrayList<CmsMongo>();
        String url = "";
        for(int i=0; i < cmsMongoList.size(); i++){
            url = cmsMongoList.get(i).getTarget();
            //url = cmsList.get(i).getUrl();
            if(url.contains(cmsMongoList.get(i).getTarget())){
                cmsListLag.add(cmsMongoList.get(i));
            }
        }
        this.datuaKargatu(cmsListLag);
    }



    private void bilatuMongo(){
        StringBuilder builder=new StringBuilder();

        imgLoadin.setImage(new Image(
                        new File(
                                Utils.lortuEzarpenak().getProperty("pathToImages")+"gearloading.gif").toURI().toString()
                )
        );
        imgLoadin.setVisible(true);

        Thread taskThread=new Thread(()->{
            //sartu taulan datua
            Bilaketa bilaketa=new Bilaketa();
            bilaketa.urlIrakurri(txt_bilatu.getText()).forEach(line ->  {
                builder.append( line + System.getProperty("line.separator"));
            });

            Platform.runLater(()->{
                cmsMongoList= CmsMongoKud.getInstance().lortuCmsMongo();

                imgLoadin.setVisible(false);
            });
        });

        taskThread.start();
    }



    private void comboBoxKargatu(){
        List<Herrialdea> herrialdeLista = CmsMongoKud.getInstance().lortuHerrialdeak();
        ObservableList<Herrialdea> herrialdeak = FXCollections.observableArrayList(herrialdeLista);
        cmbx_herrialdeak.setItems(herrialdeak);
    }


    private void iragazkia(Herrialdea herrialdea){
        List<CmsMongo> cmsListLag = new ArrayList<CmsMongo>();
        if(!herrialdea.getString().equals("IRAGAZKI GABE")){
            cmsListLag=CmsMongoKud.getInstance().herrialdekoaDa(herrialdea.getString());
        }
        else{
            cmsListLag=cmsMongoListGuztiak;
        }
        cmsMongoList = cmsListLag;
        this.datuaKargatu(cmsListLag);
    }




    public void datuaKargatu(List<CmsMongo> cmsLista){
        ObservableList<CmsMongo> cmsak = FXCollections.observableArrayList(cmsMongoList);
        tbl_cms.setItems(cmsak);
    }

    public void taulaEguneratu(){


        cmsMongoList=CmsMongoKud.getInstance().lortuCmsMongo();
        cmsMongoListGuztiak=cmsMongoList;
        datuaKargatu(cmsMongoList);
//        this.comboBoxKargatu();
    }

}
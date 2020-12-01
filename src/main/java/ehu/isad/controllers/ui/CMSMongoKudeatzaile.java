package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsMongoKud;
import ehu.isad.controllers.db.WhatWebMongoKud;
import ehu.isad.model.*;
import ehu.isad.utils.Bilaketa;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public void hasieratu(){
        clmn_url.setCellValueFactory(new PropertyValueFactory<>("target"));

        clmn_url.setCellFactory(tc -> new EstekaCell());
//        clmn_url.setCellFactory(param -> new TableCell<>(){
//            @Override
//            protected void updateItem(Hyperlink item, boolean empty) {
//                final Hyperlink hyperlink=new Hyperlink(param.getText());
//            }
//        });

        clmn_cms.setCellValueFactory(new PropertyValueFactory<>("plugins"));


        //Taula hutsa dagoenean agertzen den mezua
        tbl_cms.setPlaceholder(new Label("Ez dago emaitzik"));

        this.taulaEguneratu();


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
        List<Herrialdea> herrialdeLista = new ArrayList<>();
        for (int i=0;i<cmsMongoList.size();i++){
            var lag=cmsMongoList.get(i);
            if (lag.getPlug().getCountry()!=null){
                Herrialdea unekoa=new Herrialdea(lag.getPlug().getCountry().getModule(),lag.getPlug().getCountry().getString());
                Boolean bool=false;
                int j=0;
                while (!bool && j<herrialdeLista.size()){
                    if(herrialdeLista.get(j).getString().equals(unekoa.getString())){
                        bool=true;
                    }
                    j++;
                }
                if(!bool){
                    herrialdeLista.add(unekoa);
                }
            }
        }
        herrialdeLista.add(new Herrialdea("","IRAGAZKI GABE"));
        ObservableList<Herrialdea> herrialdeak = FXCollections.observableArrayList(herrialdeLista);
        cmbx_herrialdeak.setItems(herrialdeak);
    }

    private void iragazkia(Herrialdea herrialdea){
        if(!herrialdea.getString().equals("IRAGAZKI GABE")){
            List<CmsMongo> cmsListLag = new ArrayList<CmsMongo>();
            cmsMongoList.forEach((p)->{
                if (p.getPlug().getCountry()!=null){
                    if (p.getPlug().getCountry().getString().equals(herrialdea.getString())){
                        cmsListLag.add(p);
                    }

                }
            });
            cmsMongoList = cmsListLag;
            this.datuaKargatu(cmsListLag);
        }
        else{
            cmsMongoList=cmsMongoListGuztiak;
            this.datuaKargatu(cmsMongoListGuztiak);
        }
    }

    public void datuaKargatu(List<CmsMongo> cmsLista){
        ObservableList<CmsMongo> cmsak = FXCollections.observableArrayList(cmsMongoList);
        tbl_cms.setItems(cmsak);
    }

    public void taulaEguneratu(){
        cmsMongoList=CmsMongoKud.getInstance().lortuCmsMongo();
        cmsMongoListGuztiak=cmsMongoList;
        datuaKargatu(cmsMongoList);
        this.comboBoxKargatu();
    }

}
package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.WhatWebKud;
import ehu.isad.model.Herrialdea;
import ehu.isad.model.HyperLinkCell;
import ehu.isad.utils.Bilaketa;
import ehu.isad.model.CmsSQL;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CMSSQLKudeatzaile implements Initializable {

    private List<CmsSQL> cmsList;
    private List<CmsSQL> cmsListGuziak;

    @FXML
    private ComboBox<Herrialdea> cmbx_herrialdeak;

    @FXML
    private ImageView imgLoadin;

    @FXML
    private TextField txt_bilatu;

    @FXML
    private TableView<CmsSQL> tbl_cms;

    @FXML
    private TableColumn<CmsSQL, Hyperlink> clmn_url;

    @FXML
    private TableColumn<CmsSQL, String> clmn_cms;

    @FXML
    private TableColumn<CmsSQL, String> clmn_version;

    @FXML
    private TableColumn<CmsSQL, String> clmn_lastupdate;

    public CMSSQLKudeatzaile() {

    }

    @FXML
    void onClickAddURL(ActionEvent event) {
        if(!txt_bilatu.getText().equals("")){
            Boolean emaitza=WhatWebKud.getInstantzia().jadaBilatuta(txt_bilatu.getText());
            if(!emaitza){

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

                    try {
                        WhatWebKud.getInstantzia().insertIrakurri();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(()->{
                        //eguneratu taula
                        cmsList = CmsKud.getInstantzia().lortuCmsak();
                        this.datuaKargatu(cmsList);
                        imgLoadin.setVisible(false);
                    });
                });

                taskThread.start();
            }
            else {
                txt_bilatu.setText("");
                System.out.println("jada URL bilatu duzu");
            }
        }
        else{
            txt_bilatu.setText("URL bat sartu mesedez");
        }
    }


    @FXML
    void onKlikEgin(MouseEvent event) {
        txt_bilatu.setText("");
    }

    @FXML
    void onTestuaAldatuDa(KeyEvent event) {
        this.bilaketak(txt_bilatu.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Nola bistaratu gelaxkak (zutabearen arabera)
        // Get value from property of UserAccount.
        tbl_cms.setEditable(true);
        clmn_cms.setCellValueFactory(new PropertyValueFactory<>("cms"));
        clmn_lastupdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        clmn_url.setCellValueFactory(new PropertyValueFactory<>("url"));
        clmn_version.setCellValueFactory(new PropertyValueFactory<>("version"));
        clmn_url.setCellFactory(new HyperLinkCell());

        //add your data to the table here.
        this.taulaEguneratu();

        //Taula hutsa dagoenean agertzen den mezua
        tbl_cms.setPlaceholder(new Label("Ez dago emaitzik"));

        //comboBox-a kargatu
        this.comboBoxKargatu();

        //Adding action to the choice box
        cmbx_herrialdeak.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    this.iragazkia(cmbx_herrialdeak.getItems().get(new_val.intValue()));
                });

    }



    public void datuaKargatu(List<CmsSQL> cmsLista){
        ObservableList<CmsSQL> cmsak = FXCollections.observableArrayList(cmsLista);
        tbl_cms.setItems(cmsak);
    }

    private void bilaketak(String testua){
        List<CmsSQL> cmsListLag = new ArrayList<CmsSQL>();
        String url = "";
        for(int i=0; i < cmsList.size(); i++){
            url = cmsList.get(i).getUrl().getText();
            //url = cmsList.get(i).getUrl();
            if(url.contains(testua)){
                cmsListLag.add(cmsList.get(i));
            }
        }
        this.datuaKargatu(cmsListLag);
    }

    private void iragazkia(Herrialdea herrialdea){
        List<CmsSQL> cmsListLag = new ArrayList<CmsSQL>();
        if(!herrialdea.getString().equals("IRAGAZKI GABE")){
            String url = "";
            for(int i=0; i < cmsListGuziak.size(); i++){
                url = cmsListGuziak.get(i).getUrl().getText();
                if(CmsKud.getInstantzia().herrialdekoaDa(herrialdea.getString(),url,herrialdea.getModule())){
                    cmsListLag.add(cmsListGuziak.get(i));
                }
            }
        }
        else{
            cmsListLag=cmsListGuziak;
        }
        cmsList = cmsListLag;
        this.datuaKargatu(cmsListLag);
    }

    public void taulaEguneratu(){
        cmsList = CmsKud.getInstantzia().lortuCmsak();
        cmsListGuziak=cmsList;
        datuaKargatu(cmsList);
        this.comboBoxKargatu();
    }

    private void comboBoxKargatu(){
        List<Herrialdea> herrialdeLista = CmsKud.getInstantzia().lortuHerrialdeak();
        ObservableList<Herrialdea> herrialdeak = FXCollections.observableArrayList(herrialdeLista);
        cmbx_herrialdeak.setItems(herrialdeak);
    }
}

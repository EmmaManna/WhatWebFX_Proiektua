package ehu.isad.controllers.ui;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ehu.isad.WhatWebFX;
import ehu.isad.controllers.db.CmsKud;
import ehu.isad.controllers.db.WhatWebSQLKud;
import ehu.isad.model.Herrialdea;
import ehu.isad.model.HyperLinkCell;
import ehu.isad.utils.Bilaketa;
import ehu.isad.model.Cms;
import ehu.isad.utils.Sarea;
import ehu.isad.utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class CMSKudeatzaile implements Initializable {

    private List<Cms> cmsList;
    private List<Cms> cmsListGuziak;
    private WhatWebFX mainApp;
    private ImageView screenshot = new ImageView();
    private Popup pop = new Popup();
    private Boolean ikusten = false;


    public CMSKudeatzaile(WhatWebFX mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private ComboBox<Herrialdea> cmbx_herrialdeak;

    @FXML
    private ImageView imgLoadin;

    @FXML
    private TextField txt_bilatu;

    @FXML
    private TableView<Cms> tbl_cms;

    @FXML
    private TableColumn<Cms, Button> clmn_screenshot;

    @FXML
    private TableColumn<Cms, Hyperlink> clmn_url;

    @FXML
    private TableColumn<Cms, String> clmn_cms;

    @FXML
    private TableColumn<Cms, String> clmn_version;

    @FXML
    private TableColumn<Cms, String> clmn_lastupdate;

    @FXML
    void onClickAddURL(ActionEvent event) {
        //Add url botoia sakatzean, WhatWeb-i egiten zaio dei eta datuak sartzen dira DB-an
        //Bitartean kargatzen dagoela adieraziko da,
        // URL-rik sartu ez bada mezu bat agertuko da. Jada bago ez da ezer egingo.
        if(!txt_bilatu.getText().equals("")){
            Boolean emaitza=WhatWebSQLKud.getInstantzia().jadaBilatuta(txt_bilatu.getText());

            if(!emaitza){
                StringBuilder builder=new StringBuilder();
                imgLoadin.setImage(new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"gearloading.gif").toURI().toString()));
                imgLoadin.setVisible(true);

                Thread taskThread=new Thread(()->{
                    Bilaketa bilaketa=new Bilaketa();
                    bilaketa.urlIrakurri(txt_bilatu.getText()).forEach(line ->  {
                        builder.append( line + System.getProperty("line.separator"));
                    });

                    try {
                        WhatWebSQLKud.getInstantzia().insertIrakurri();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(()->{
                        //eguneratu taula
                        cmsListGuziak = CmsKud.getInstantzia().lortuCmsak();
                        cmsList = CmsKud.getInstantzia().lortuCmsak();
                        this.datuaKargatu(cmsList);
                        imgLoadin.setVisible(false);
                    });
                });
                taskThread.start();
            }
            else {
                txt_bilatu.setText(txt_bilatu.getText()+" jada gordeta dago");
            }
        }
        else{
            txt_bilatu.setText("URL bat sartu mesedez");
        }
    }


    public CMSKudeatzaile() { }


    @FXML
    void onKlikEgin(MouseEvent event) {
        //Bilaketa kutxan klik egitean zegoen testua ezabatzen da
        txt_bilatu.setText("");
    }


    @FXML
    void onTestuaAldatuDa(KeyEvent event) {
        //Bilaketa kutxan letra bat idatzen den bakoitzean taula eguneratzen da
        this.bilaketak(txt_bilatu.getText());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Popup hasieratu
        screenshot.setImage(new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"nopreview.png").toURI().toString()));
        pop.getContent().add(screenshot);

        //Botoiak gehitu
        addButtonToTable();
        clmn_screenshot.setStyle( "-fx-alignment: CENTER;");

       //Zutabeak hasieratu
        this.zutabeakHasieratu();

        //add your data to the table here.
        this.taulaEguneratu();

        //Taula hutsa dagoenean agertzen den mezua
        tbl_cms.setPlaceholder(new Label("Ez dago emaitzik"));

        //comboBox-a kargatu
        this.comboBoxKargatu();

        //ComboBox-ari akzioa gehitu
        this.comboBoxAction();
    }


    private void zutabeakHasieratu(){
        // Nola bistaratu gelaxkak (zutabearen arabera)
        // Get value from property of UserAccount.
        tbl_cms.setEditable(true);
        clmn_cms.setCellValueFactory(new PropertyValueFactory<>("cms"));
        clmn_lastupdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        clmn_url.setCellValueFactory(new PropertyValueFactory<>("url"));
        clmn_version.setCellValueFactory(new PropertyValueFactory<>("version"));
        clmn_url.setCellFactory(new HyperLinkCell());
    }


    private void comboBoxAction(){
        //ComboBox-ean aldaketa bat dagoenean iragazkia aplikatzen da aukeratutako herrialdearen arabera
        cmbx_herrialdeak.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    if(new_val.intValue()>-1){
                        this.iragazkia(cmbx_herrialdeak.getItems().get(new_val.intValue()));
                    }
                });
    }


    public void datuaKargatu(List<Cms> cmsLista){
        //Taulan jarri beharreko datuak kargatzen dira
        ObservableList<Cms> cmsak = FXCollections.observableArrayList(cmsLista);
        tbl_cms.setItems(cmsak);
    }


    private void bilaketak(String testua){
        //Bilaketa bat egitean emandako testua duten URL-ak lortzen dira, eta taula eguneratzen da
        List<Cms> cmsListLag = new ArrayList<Cms>();
        String url = "";
        for(int i=0; i < cmsList.size(); i++){
            url = cmsList.get(i).getUrl().getText();

            if(url.contains(testua)){
                cmsListLag.add(cmsList.get(i));
            }
        }
        this.datuaKargatu(cmsListLag);
    }


    private void iragazkia(Herrialdea herrialdea){
        //Zehaztutako herraldean zerbitzaria duten URL-ak lortu eta taula eguneratzen da
        List<Cms> cmsListLag = new ArrayList<Cms>();
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
        //Datu berriak satzen direnean, datuak DB-tik lortzen dira
        //Taula eta comboBox-a eguneratzen dira
        cmsList = CmsKud.getInstantzia().lortuCmsak();
        cmsListGuziak=cmsList;
        datuaKargatu(cmsList);
        this.comboBoxKargatu();
    }


    private void comboBoxKargatu(){
        //ComboBox-ean datuak sartzen dira
        List<Herrialdea> herrialdeLista = CmsKud.getInstantzia().lortuHerrialdeak();
        ObservableList<Herrialdea> herrialdeak = FXCollections.observableArrayList(herrialdeLista);
        cmbx_herrialdeak.setItems(herrialdeak);
    }


    private void addButtonToTable() {
        //Botoiak taulako errenkada bakoitzean gehitzen ditu, eta propietateak zehazten dira
        Callback<TableColumn<Cms, Button>, TableCell<Cms, Button>> cellFactory = new Callback<TableColumn<Cms, Button>, TableCell<Cms, Button>>() {
            @Override
            public TableCell<Cms, Button> call(final TableColumn<Cms, Button> param) {
                final TableCell<Cms, Button> cell = new TableCell<Cms, Button>() {

                    private final Button btn = new Button("");
                    {
                        btn.getStyleClass().add("screenshot");
                        btn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.CAMERA_RETRO, "1.5em"));

                        //Botoian klik egitean Screenshot-a lortzen eta bistaratzen da
                        btn.setOnAction((ActionEvent event) -> {
                            TableRow<Cms> errenkada = getTableRow();
                            ButtonSetOnAction(btn,errenkada);
                        });

                        //Sagua botoiaren jainean jarrita badago preview-a agertzen da
                        btn.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                            TableRow<Cms> errenkada = getTableRow();
                            ButtonHover(btn, errenkada, newValue);
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        clmn_screenshot.setCellFactory(cellFactory);
    }


    private void ButtonSetOnAction(Button btn, TableRow<Cms> errenkada){
        //Botoian klik egitean Screenshot-a lortzen eta bistaratzen da
        //Lehengo jada sisteman dagoen konprobatuko da
        pop.hide();
        imgLoadin.setImage(new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+"gearloading.gif").toURI().toString()));
        imgLoadin.setVisible(true);

        Thread taskThread=new Thread(()->{
            Sarea s = new Sarea();
            if(!irudiaBadago(ezabatuAtzekoa(errenkada.getItem().getUrl().getText()))) {
                s.irudiaLortu(errenkada.getItem().getUrl().getText());
            }
            Platform.runLater(()->{
                imgLoadin.setVisible(false);
                ikusten=true;
                String irudia = errenkada.getItem().getUrl().getText();
                irudia = ezabatuAtzekoa(irudia);
                Bounds bounds = btn.localToScene(btn.getBoundsInLocal());
                irudiaBistaratu(irudia, bounds.getMinX(), bounds.getMinY());
            });
        });
        taskThread.start();
    }


    private void ButtonHover(Button btn, TableRow<Cms> errenkada, Boolean newValue){
        //Sagua botoiaren jainean jarrita badago preview-a agertzen da
        //Lehenengo Screeshot-a bistaratuta ez dagoela konporbatzen du
        if (newValue&!pop.isShowing()) {
            ikusten = false;
            PopupPrestatu(errenkada);

            btn.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(!ikusten){
                        double xOffset = event.getScreenX();
                        double yOffset = event.getScreenY();
                        pop.setX(xOffset-event.getX()+35);
                        pop.setY(yOffset-event.getY());
                    }
                }
            });
            pop.show(mainApp.getStage());
        }
        else {
            pop.hide();
            screenshot.setVisible(false);
        }
    }


    private void PopupPrestatu(TableRow<Cms> errenkada){
        //PopUp-a preview-a ikusteko tamaina eta irudia jartzen dio
        String irudia = errenkada.getItem().getUrl().getText();
        irudiaPrestatu(irudia);
        pop.getContent().remove(0);
        pop.setAnchorX(200);
        pop.setAnchorY(100);
        pop.getContent().add(screenshot);
    }


    private String ezabatuAtzekoa(String izena){
        //Emandako String-ari, URL-a, agertzen den lehenengo / aurrera dagoen guztia ezabatu
        izena= izena.replace("https://","");
        izena=izena.replace("http://","");
        while(izena.contains("/")){
            izena=izena.substring(0,izena.length()-1);
        }
        return izena;
    }


    private void irudiaPrestatu(String irudia){
        //Preview bistaratzeko prestatzen da: deskargatuta dagoen begiratzen da,
        //Eta horren arabera zein jarriko den erabakitzen da (Irudia edo no Preview Available)
        irudia = ezabatuAtzekoa(irudia);
        if(!irudiaBadago(irudia)){
            irudia = "nopreview";
        }
        screenshot.setImage(new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+irudia+".png").toURI().toString()));
        screenshot.setFitHeight(125);
        screenshot.setFitWidth(200);
        screenshot.setVisible(true);
    }


    private Boolean irudiaBadago(String irudia){
        //Zehaztutako izena duen irudia sisteman gordeta dagoen konprobatzen du
        File f =  new File(Utils.lortuEzarpenak().getProperty("pathToImages")+irudia+".png");
        return f.exists();
    }


    private void irudiaBistaratu(String irudia, double x, double y){
        //Screenshot-a handi bistaratzeko Popup-a prestatzen da
        if(!irudiaBadago(irudia)){
            irudia = "nopreview";
        }
        screenshot.setImage(new Image(new File(Utils.lortuEzarpenak().getProperty("pathToImages")+irudia+".png").toURI().toString()));
        screenshot.setFitHeight(525);
        screenshot.setFitWidth(700);
        screenshot.setVisible(true);

        pop.getContent().remove(0);
        pop.setAnchorX(700);
        pop.setAnchorY(525);
        pop.setX(x+500);
        pop.setY(y+100);

        pop.getContent().add(screenshot);
        if(!pop.isShowing()){
            pop.show(mainApp.getStage());
        }
        pop.setAutoHide(true);
    }
}

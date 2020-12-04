package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsMongoKud;
import ehu.isad.controllers.db.ServerKud;
import ehu.isad.model.CmsMongo;
import ehu.isad.model.MongoErabiltzailea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import java.util.List;


public class ServerKudeatzaile {

    private List<String> targets;

    private List<CmsMongo> mongoList;

    @FXML
    private TableView<String> tblServer;

    @FXML
    private TableColumn<String, String> zutabeServer;

    @FXML
    private ComboBox<String> cmbx_servers;


    public void hasieratu(){
        if(targets!=null){
            targets.clear();
        }
        else targets=new ArrayList<>();

        if(MongoErabiltzailea.getInstance().getCollection().equals("")){
            TaulaKargatuSQL();
        }
        else {
            TaulaKargatuMongo();
        }

        //Taula hutsa dagoenean agertzen den mezua
        tblServer.setPlaceholder(new Label("Ez dago emaitzik"));

        //comboBox-a kargatu
        if(MongoErabiltzailea.getInstance().getCollection().equals("")) {
           ComboBoxKargatuSQL();
        }
        else {
            ComboBoxKargatuMongo();
        }

        //ComboBox-ari Acton gehitu
        ComboBoxAction();
    }


    private void TaulaKargatuSQL(){
        //SQLite DB erabiltzean datuak kargatu taulan
        ServerKud serverKud=ServerKud.getInstantzia();
        targets = serverKud.lortuTargets();
        kargatuLista(targets);
    }


    private void ComboBoxKargatuSQL(){
        //SQLite DB erabiltzean datuak kargatu ComboBox-ean
        List<String> serverLista = ServerKud.getInstantzia().lortuZerbitzaria();
        ObservableList<String> zerbitzariak = FXCollections.observableArrayList(serverLista);
        cmbx_servers.setItems(zerbitzariak);
    }


    private void TaulaKargatuMongo(){
        //MongoDB erabiltzean datuak kargatu taulan
        mongoList=CmsMongoKud.getInstance().lortuCmsMongo();
        mongoList.forEach((unekoa)->
        {
            targets.add(unekoa.getTarget());
        });
        kargatuLista(targets);
    }


    private void ComboBoxKargatuMongo(){
        //MongoDB erabiltzean datuak kargatu ComboBox-ean
        List<String> listaLag=new ArrayList<>();
        mongoList.forEach((unekoa)->{
            if(unekoa.getPlug().getMetaGenerator()!=null){
                if (!listaLag.contains(unekoa.getPlug().getMetaGenerator().toString())){
                    listaLag.add(unekoa.getPlug().getMetaGenerator().toString());
                }
            }

        });
        listaLag.add("Iragazki Gabe");
        cmbx_servers.setItems(FXCollections.observableArrayList(listaLag));
    }


    private void ComboBoxAction(){
        //ComboBox-ean balio berri bat aukeratzen denean filtroa aplikatzen da
        cmbx_servers.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    if(new_val.intValue() > -1){
                        this.zerbitzariak(cmbx_servers.getItems().get(new_val.intValue()));
                    }
                });
    }


    private void kargatuLista(List<String> targetLista){
        //Taulan datuak kargatzen dira
        ObservableList<String> lista= FXCollections.observableArrayList(targetLista);
        var emaitza=lista;

        zutabeServer.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue()));

        tblServer.setItems(emaitza);
    }


    private void zerbitzariak(String zerbitzaria){
        //Iragazki moduan erabiltzen diren zerbitzari motak lortu eta gordentzen dira MongoDB-n
        List<String> zerbitzariakListLag = new ArrayList<String>();
        if(!zerbitzaria.equals("Iragazki Gabe")){
            if(MongoErabiltzailea.getInstance().getCollection().equals("")){
                ZerbitzariakKargatuMongo(zerbitzariakListLag, zerbitzaria);
            }
            else{
               ZerbitzariakKargatuMongoCollection(zerbitzariakListLag,zerbitzaria);
            }
        }
        else{
            zerbitzariakListLag=targets;
        }
        kargatuLista(zerbitzariakListLag);
    }


    private void ZerbitzariakKargatuMongo(List<String> zerbitzariakListLag, String zerbitzaria){
        //TODO: No sé que hace esto JonQ
        String url = "";
        for(int i=0; i < targets.size(); i++){
            url = targets.get(i);
            if(ServerKud.getInstantzia().zerbitzariaDa(zerbitzaria,url)){
                zerbitzariakListLag.add(targets.get(i));
            }
        }
    }


    private void ZerbitzariakKargatuMongoCollection(List<String> zerbitzariakListLag, String zerbitzaria){
        //TODO: No sé que hace esto JonQ
        List<String> finalZerbitzariakListLag = zerbitzariakListLag;
        mongoList.forEach((unekoa)->{
            if(unekoa.getPlug().getMetaGenerator()!=null){
                if(unekoa.getPlug().getMetaGenerator().toString().equals(zerbitzaria)){
                    finalZerbitzariakListLag.add(unekoa.getTarget());
                }
            }
        });
        zerbitzariakListLag=finalZerbitzariakListLag;
    }

}

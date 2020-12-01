package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.CmsMongoKud;
import ehu.isad.controllers.db.ServerKud;
import ehu.isad.model.CmsMongo;
import ehu.isad.model.MongoErabiltzailea;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        targets=new ArrayList<>();
        if(MongoErabiltzailea.getInstance().getCollection().equals("")){
            ServerKud serverKud=ServerKud.getInstantzia();
            targets = serverKud.lortuTargets();
            kargatuLista(targets);

        }
        else {
            mongoList=CmsMongoKud.getInstance().lortuCmsMongo();
            mongoList.forEach((unekoa)->
            {
                targets.add(unekoa.getTarget());
            });
            kargatuLista(targets);
        }

        //Taula hutsa dagoenean agertzen den mezua
        tblServer.setPlaceholder(new Label("Ez dago emaitzik"));

        //comboBox-a kargatu
        if(MongoErabiltzailea.getInstance().getCollection().equals("")) {
            List<String> serverLista = ServerKud.getInstantzia().lortuZerbitzaria();
            ObservableList<String> zerbitzariak = FXCollections.observableArrayList(serverLista);
            cmbx_servers.setItems(zerbitzariak);
        }
        else {
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

        //Adding action to the choice box
        cmbx_servers.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    this.zerbitzariak(cmbx_servers.getItems().get(new_val.intValue()));
                });
    }

    private void kargatuLista(List<String> targetLista){
        ObservableList<String> lista= FXCollections.observableArrayList(targetLista);
        var emaitza=lista;

        zutabeServer.setCellValueFactory(data->
                new SimpleStringProperty(data.getValue()));

        tblServer.setItems(emaitza);
    }

    @FXML
    void onClickEguneratu(ActionEvent event) {
        hasieratu();
        cmbx_servers.setValue("Iragazki Gabe");
    }

    private void zerbitzariak(String zerbitzaria){
        List<String> zerbitzariakListLag = new ArrayList<String>();
        if(!zerbitzaria.equals("Iragazki Gabe")){
            if(MongoErabiltzailea.getInstance().getCollection().equals("")){
                String url = "";
                for(int i=0; i < targets.size(); i++){
                    url = targets.get(i);
                    if(ServerKud.getInstantzia().zerbitzariaDa(zerbitzaria,url)){
                        zerbitzariakListLag.add(targets.get(i));
                    }
                }
            }
            else{
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
        else{
            zerbitzariakListLag=targets;
        }
        kargatuLista(zerbitzariakListLag);
    }
}

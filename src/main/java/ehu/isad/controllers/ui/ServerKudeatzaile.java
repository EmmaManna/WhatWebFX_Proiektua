package ehu.isad.controllers.ui;

import ehu.isad.controllers.db.ServerKud;
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

public class ServerKudeatzaile implements Initializable {

    private List<String> targets;

    @FXML
    private TableView<String> tblServer;

    @FXML
    private TableColumn<String, String> zutabeServer;

    @FXML
    private ComboBox<String> cmbx_servers;

    public void hasieratu(){
        ServerKud serverKud=ServerKud.getInstantzia();
        targets = serverKud.lortuTargets();
        kargatuLista(targets);

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hasieratu();
        //Taula hutsa dagoenean agertzen den mezua
        tblServer.setPlaceholder(new Label("Ez dago emaitzik"));

        //comboBox-a kargatu
        List<String> serverLista = ServerKud.getInstantzia().lortuZerbitzaria();
        ObservableList<String> zerbitzariak = FXCollections.observableArrayList(serverLista);
        cmbx_servers.setItems(zerbitzariak);

        //Adding action to the choice box
        cmbx_servers.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                   this.zerbitzariak(cmbx_servers.getItems().get(new_val.intValue()));
                });

    }

    private void zerbitzariak(String zerbitzaria){
        List<String> zerbitzariakListLag = new ArrayList<String>();
        if(!zerbitzaria.equals("Iragazki Gabe")){
            String url = "";
            for(int i=0; i < targets.size(); i++){
                url = targets.get(i);
                if(ServerKud.getInstantzia().zerbitzariaDa(zerbitzaria,url)){
                    zerbitzariakListLag.add(targets.get(i));
                }
            }
        }
        else{
            zerbitzariakListLag=targets;
        }
        kargatuLista(zerbitzariakListLag);
    }
}

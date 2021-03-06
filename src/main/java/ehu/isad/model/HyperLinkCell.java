package ehu.isad.model;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HyperLinkCell implements  Callback<TableColumn<Cms, Hyperlink>, TableCell<Cms, Hyperlink>> {
    @Override
    public TableCell<Cms, Hyperlink> call(TableColumn<Cms, Hyperlink> arg) {
        TableCell<Cms, Hyperlink> cell = new TableCell<Cms, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                //Url bat badago eta klik egiten bazaio sistema eragilearen nabigatzailea ireki eta bilatzen du.
                if (item != null) {
                    item.setOnAction(e -> {
                        String url = item.getText();
                        System.out.println( url );
                        if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                try {
                                    Desktop.getDesktop().browse( new URI(url) );
                                } catch (IOException | URISyntaxException e1) {
                                    e1.printStackTrace();
                                }
                            }).start();
                        }
                    });
                }
                setGraphic(item);
            }

        };
        return cell;
    }
}

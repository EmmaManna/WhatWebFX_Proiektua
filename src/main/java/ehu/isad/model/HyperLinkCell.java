package ehu.isad.model;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HyperLinkCell implements  Callback<TableColumn<CmsSQL, Hyperlink>, TableCell<CmsSQL, Hyperlink>> {
    @Override
    public TableCell<CmsSQL, Hyperlink> call(TableColumn<CmsSQL, Hyperlink> arg) {
        TableCell<CmsSQL, Hyperlink> cell = new TableCell<CmsSQL, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                if (item != null) {
                    item.setOnAction(e -> {
                        String url = item.getText();
                        try {
                            System.out.println( url );
                            Desktop.getDesktop().browse(new URI(url));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }

                    });
                }

                setGraphic(item);
            }

        };
        return cell;
    }
}

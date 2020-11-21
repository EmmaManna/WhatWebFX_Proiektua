package ehu.isad.model;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HyperLinkCell implements  Callback<TableColumn<Cms, Hyperlink>, TableCell<Cms, Hyperlink>> {
    @Override
    public TableCell<Cms, Hyperlink> call(TableColumn<Cms, Hyperlink> arg) {
        TableCell<Cms, Hyperlink> cell = new TableCell<Cms, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                setGraphic(item);
            }
        };
        return cell;
    }
}

package atlantafx.sampler.staff.page.components;

import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.base.service.cashier.TableCoffeeService;
import atlantafx.sampler.base.util.Lazy;
import atlantafx.sampler.staff.page.OutlinePage;
import atlantafx.sampler.staff.page.dialog.TableDialog;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public final class TableListPage extends OutlinePage {
    public static final String NAME = "Table List Page";


    public TableListPage
            () {
        super();

    }


    @Override
    public String getName() {
        return "";
    }
}


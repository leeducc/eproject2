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
    private Lazy<TableDialog> tableDialog;
    private static String title;

    // Map to hold table buttons and their occupied state
    private final HashMap<Button, Integer> tableButtonMap = new HashMap<>();

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        TableListPage.title = title;
    }

    @Override
    public String getName() {
        return NAME;
    }

    // No-argument constructor
    public TableListPage() {
        super(); // Provide default parameters
        createGrid();

        tableDialog = new Lazy<>(() -> new TableDialog(getTitle(), new CashierService(), 1));
    }

    private void openTableDialog() {
        var dialog = tableDialog.get();
        dialog.show(getScene());
        Platform.runLater(dialog::requestFocus);
    }

    private void createGrid() {
        GridPane floorTable = new GridPane();
        floorTable.setPadding(new Insets(10));
        floorTable.setHgap(20);
        floorTable.setVgap(20);
        floorTable.setAlignment(Pos.CENTER);

        GridPane upstairTable = new GridPane();
        upstairTable.setPadding(new Insets(10));
        upstairTable.setHgap(20);
        upstairTable.setVgap(20);
        upstairTable.setAlignment(Pos.CENTER);

        ArrayList<String> floorTables = TableCoffeeService.getNameTable(1);
        addButtonsToGrid(floorTable, floorTables);

        ArrayList<String> upstairTables = TableCoffeeService.getNameTable(2);
        addButtonsToGrid(upstairTable, upstairTables);

        Label labelFloorTables = new Label("Floor Tables");
        labelFloorTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label labelUpstairTables = new Label("Upstair Tables");
        labelUpstairTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");



        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(labelFloorTables, floorTable, labelUpstairTables, upstairTable);
        getChildren().add(layout);
    }

    private void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames) {
        int count = 0;
        int rows = 6;
        int cols = 6;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (count >= tableNames.size()) {
                    break;
                }

                Button tableButton = new Button(tableNames.get(count));
                tableButton.setPrefSize(90, 90);
                int finalCount = count;

                // Add the button to the map with its index
                tableButtonMap.put(tableButton, finalCount);

                tableButton.setOnAction(event -> {
                    setTitle(tableNames.get(finalCount));
                    openTableDialog();
                });

                grid.add(tableButton, col, row);
                count++;
            }
        }
    }



}


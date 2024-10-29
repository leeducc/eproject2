package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.base.entity.common.Bill;
import atlantafx.sampler.base.entity.common.Tables;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.base.service.cashier.TableCoffeeService;
import atlantafx.sampler.base.util.Lazy;
import atlantafx.sampler.cashier.page.OutlinePage;
import atlantafx.sampler.cashier.page.dialog.TableDialog; // Import TableDialog
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TableListPage extends OutlinePage {

    public static final String NAME = "Table List Page";
    private static String title;
    private GridPane grid;
    private int currentPage = 1;
    private final int itemsPerPage = 20;
    private String currentKeyword = ""; // Store the current search keyword
    private TextField searchField;
    private Lazy<TableDialog> tableDialog ;
    private Label selectedTableLabel;// Text field for keyword search

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

    public TableListPage() {
        super();
        tableDialog = new Lazy<>(() -> new TableDialog(getTitle()));
        createGrid();
    }

    private void openTableDialog(String tableName) {
        TableDialog dialog = new TableDialog(tableName); // Create a new instance for each table
        dialog.show(getScene());
        Platform.runLater(dialog::requestFocus);
    }

    // Creates the grid, search field, and pagination controls
    private void createGrid() {
        VBox layout = new VBox();
        layout.getStyleClass().add("vbox");

        Label labelFloorTables = new Label("List Tables");
        labelFloorTables.getStyleClass().add("label-title");

        // Search TextField for filtering
        searchField = new TextField();
        searchField.setPromptText("Search by table name...");
        searchField.getStyleClass().add("text-field");
        searchField.setOnKeyReleased(this::handleSearch);

        // Create GridPane for tables
        grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        // Load initial table data based on the current page and search keyword
        ArrayList<String> floorTables = TableCoffeeService.getNameTable(currentPage, itemsPerPage, currentKeyword);
        addButtonsToGrid(grid, floorTables);

        // Pagination controls
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        prevButton.getStyleClass().add("button");
        nextButton.getStyleClass().add("button");

        prevButton.setOnAction(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshGrid();
            }
        });

        nextButton.setOnAction(e -> {
            if ((currentPage - 1) * itemsPerPage + floorTables.size() < TableCoffeeService.getFilteredTableCount(currentPage, itemsPerPage, currentKeyword)) {
                currentPage++;
                refreshGrid();
            }
        });

        HBox paginationControls = new HBox(10, prevButton, nextButton);
        paginationControls.getStyleClass().add("pagination");

        // Add status legend
        VBox statusBox = new VBox();
        statusBox.getStyleClass().add("status-box");
        setupStatusLegend(statusBox);

        layout.getChildren().addAll(labelFloorTables, searchField, grid, statusBox, paginationControls);

        getChildren().clear();
        getChildren().add(layout);
    }

    private void handleSearch(javafx.scene.input.KeyEvent keyEvent) {
        currentKeyword = searchField.getText().trim();
        currentPage = 1; // Reset to first page after search
        refreshGrid();
    }

    private void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames) {
        grid.getChildren().clear(); // Clear previous buttons

        for (int i = 0; i < tableNames.size(); i++) {
            String tableName = tableNames.get(i);
            Button tableButton = new Button(tableName);
            tableButton.setPrefSize(200, 200);

            checkAndSetTableStatus(tableButton, tableName); // Set initial status based on temporary orders

            // Set click event for handling table actions
            tableButton.setOnAction(e -> handleTableButtonClick(tableButton, TableCoffeeService.getTableByName(tableName)));

            grid.add(tableButton, i % 4, i / 4); // Adjusts layout to 4 columns per row
        }
    }

    private void refreshGrid() {
        ArrayList<String> floorTables = TableCoffeeService.getNameTable(currentPage, itemsPerPage, currentKeyword);
        addButtonsToGrid(grid, floorTables);
    }

    private void setupStatusLegend(VBox statusBox) {
        Label reservedLabel = new Label("Đã đặt");
        reservedLabel.setStyle(
                "-fx-background-color: #28a745; " +  // A green color for a reserved state
                        "-fx-text-fill: #ffffff; " +         // White text for good contrast
                        "-fx-padding: 8px 16px; " +          // Padding for a spacious look
                        "-fx-pref-width: 150px; " +
                        "-fx-alignment: center; " +
                        "-fx-background-radius: 8px;"       // Rounded corners for a modern look
        );

        Label unavailableLabel = new Label("Đang dọn");
        unavailableLabel.setStyle(
                "-fx-background-color: #ff9800; " +  // A vibrant orange color for cleaning status
                        "-fx-text-fill: #ffffff; " +         // White text for contrast
                        "-fx-padding: 8px 16px; " +
                        "-fx-pref-width: 150px; " +
                        "-fx-alignment: center; " +
                        "-fx-background-radius: 8px;"
        );

        Label availableLabel = new Label("Còn Chỗ");
        availableLabel.setStyle(
                "-fx-background-color: #dcdcdc; " +  // A subtle gray for available status
                        "-fx-text-fill: #000000; " +         // Black text for better readability
                        "-fx-padding: 8px 16px; " +
                        "-fx-pref-width: 150px; " +
                        "-fx-alignment: center; " +
                        "-fx-background-radius: 8px;"
        );

        // Adjusting the widths to keep uniformity.
        availableLabel.setMinWidth(150);
        reservedLabel.setMinWidth(150);

        statusBox.getChildren().addAll(availableLabel, unavailableLabel, reservedLabel);
    }

    // Checks if a table has items in temporary_order and sets its status to Reserved if so
    private void checkAndSetTableStatus(Button tableButton, String tableName) {
        boolean hasTemporaryOrder = TableCoffeeService.hasTemporaryOrder(tableName);
        Tables table = TableCoffeeService.getTableByName(tableName);

        if (hasTemporaryOrder) {
            table.setStatusId(1); // Reserved
            TableCoffeeService.updateStatusTable(1, tableName);
            tableButton.setStyle("-fx-background-color: green;"); // Reserved color
        } else {
            table.setStatusId(3); // Available
            TableCoffeeService.updateStatusTable(3, tableName);
            tableButton.setStyle("-fx-background-color: lightgray;"); // Available color
        }
    }

    private void handleTableButtonClick(Button tableButton, Tables table) {
        int status = table.getStatusId();

        if (status == 2) { // Cleaning status
            Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Xác nhận bàn đã dọn xong");
            confirmationDialog.setHeaderText("Bạn có chắc chắn là bàn này khách đã dời đi và đã dọn xong");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                TableCoffeeService.updateStatusTable(3, table.getName()); // Set to Available
                table.setStatusId(3);
                tableButton.setStyle("-fx-background-color: lightgray;"); // Available color
            }
        } else {
            selectedTableLabel = new Label(tableButton.getText());
            TableListPage.setTitle(tableButton.getText());
            openTableDialog(selectedTableLabel.getText()); // Open the table dialog for ordering
        }
    }




    private void updateTableButtonColor(Button tableButton, Tables table) {
        int statusId = table.getStatusId();
        switch (statusId) {
            case 1 -> tableButton.setStyle("-fx-background-color: green;"); // Reserved
            case 2 -> tableButton.setStyle("-fx-background-color: orange;"); // Cleaning
            default -> tableButton.setStyle("-fx-background-color: lightgray;"); // Available
        }
    }
}

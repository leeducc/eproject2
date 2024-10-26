package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.base.entity.common.Bill;
import atlantafx.sampler.base.entity.common.Tables;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.base.service.cashier.TableCoffeeService;
import atlantafx.sampler.cashier.page.OutlinePage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;

public final class TableListPage extends OutlinePage {

  public static final String NAME = "Table List Page";
  private static Label selectedTableLabel;
  private GridPane grid;
  private static String title;
  static OrderListPage orderListPage = new OrderListPage();

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
    createGrid();
  }

  // Tạo lưới 5x5 và thêm sự kiện nhấp chuột cho các ô
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

    // Thêm các bàn vào khu B (Tầng lầu)
    ArrayList<String> upstairTables = TableCoffeeService.getNameTable(2);
    addButtonsToGrid(upstairTable, upstairTables);

    Label labelFloorTables = new Label("Floor Tables");
    labelFloorTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Label labelUpstairTables = new Label("Upstair Tables");
    labelUpstairTables.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    // Chú thích trạng thái bàn
    VBox statusBox = new VBox();
    statusBox.setSpacing(10);

    Label reservedLabel = new Label("Đã đặt");
    reservedLabel.setStyle(
        "-fx-background-color: green; -fx-text-fill: white; -fx-padding: 5px; -fx-pref-width: 150px; -fx-alignment: center;");

    Label unavailableLabel = new Label("Đang dọn");
    unavailableLabel.setStyle(
        "-fx-background-color: orange; -fx-text-fill: white; -fx-padding: 5px; -fx-pref-width: 150px; -fx-alignment: center;");

    Label availableLabel = new Label("Còn Chỗ");
    availableLabel.setStyle(
        "-fx-background-color: lightgray; -fx-text-fill: white; -fx-padding: 5px; -fx-pref-width: 150px; -fx-alignment: center;");

// Đặt chiều rộng ưu tiên để các label bằng nhau
    availableLabel.setMinWidth(150);
//        unavailableLabel.setMinWidth(150);
    reservedLabel.setMinWidth(150);

    statusBox.getChildren().addAll(availableLabel, unavailableLabel, reservedLabel);

    // Đặt lưới vào bố cục chính
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);

    layout.getChildren()
        .addAll(labelFloorTables, floorTable, labelUpstairTables, upstairTable, statusBox);
    layout.getStylesheets().add(getClass().getResource("/css/listTable.css").toExternalForm());

    // Thêm vào Scene graph
    getChildren().add(layout);
  }

  private void addButtonsToGrid(GridPane grid, ArrayList<String> tableNames) {
    int count = 0;
    int rows = 6;
    int cols = 6;

    // Xóa các nút cũ để tránh trùng lặp
    grid.getChildren().clear();

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (count >= tableNames.size()) {
          break;
        }

        // Tạo một nút cho mỗi bàn
        String tableName = tableNames.get(count);
        Button tableButton = new Button(tableName);
        tableButton.setPrefSize(90, 90);

        // Lấy thông tin trạng thái của bàn
        Tables table = TableCoffeeService.getTableByName(tableName);

        // Cập nhật màu ban đầu của nút dựa trên trạng thái của bàn
        updateTableButtonColor(tableButton, table);

        // Xử lý sự kiện khi nhấn nút
        tableButton.setOnAction(e -> {
          handleTableButtonClick(tableButton, table);
        });

        // Thêm nút vào grid
        grid.add(tableButton, col, row);
        count++;
      }
    }
  }

  // Phương thức để xử lý khi nhấn vào một nút bàn
  private void handleTableButtonClick(Button tableButton, Tables table) {
    int status = table.getStatusId();
    if (status == 2) {
      // Tạo và hiển thị dialog xác nhận
      Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
      confirmationDialog.setTitle("Xác nhận bàn đã dọn xong");
      confirmationDialog.setHeaderText("Bạn có chắc chắn là bàn này đã dọn xong");
      confirmationDialog.getDialogPane().getStylesheets().add(
          getClass().getResource("/css/cssDiaLog.css").toExternalForm()
      );

      Optional<ButtonType> result = confirmationDialog.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        TableCoffeeService.updateStatusTable(3, table.getName());
        table.setStatusId(3); // Cập nhật trạng thái của đối tượng bàn
        updateTableButtonColor(tableButton, table);
      }
    } else {
      selectedTableLabel = new Label(tableButton.getText());
      TableListPage.setTitle(tableButton.getText());
      orderListPage.showCheckOrderDialog();

      updateStatusTableByOrder(tableButton.getText());
      table.setStatusId(TableCoffeeService.getStatusByTableName(table.getName()));
      updateTableButtonColor(tableButton, table);
    }
  }

  // Phương thức để cập nhật màu sắc của nút bàn dựa trên trạng thái bàn
  private void updateTableButtonColor(Button tableButton, Tables table) {
    int statusId = table.getStatusId();
    switch (statusId) {
      case 1:
        tableButton.setStyle("-fx-background-color: green;");
        break;
      case 2:
        tableButton.setStyle("-fx-background-color: orange;");
        break;
      default:
        tableButton.setStyle("-fx-background-color: lightgray;");
        break;
    }
  }

    public static void updateStatusTableByOrder(String tableName) {
    List<Bill> newBill = CashierService.getBillByNameTable(tableName);
      Tables table = TableCoffeeService.getTableByName(tableName);
      int statusId = table.getStatusId();
    if ( !newBill.isEmpty()) {
      TableCoffeeService.updateStatusTable(1, tableName);
    }else{
      if(statusId != 2){
        TableCoffeeService.updateStatusTable(3, tableName);
      }
    }
  }
}





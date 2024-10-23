package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.cashier.page.OutlinePage;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;

public final class TableListPage extends OutlinePage {
    public static final String NAME = "Table List Page";

    private GridPane grid;

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
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);  // Căn giữa lưới
        grid.setHgap(10);  // Khoảng cách giữa các cột
        grid.setVgap(10);  // Khoảng cách giữa các hàng

        // Tạo các ô lưới 5x5
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Button cell = new Button("Cell " + (row * 5 + col + 1));
                cell.setPrefSize(100, 100);  // Kích thước của từng ô
                int cellNumber = row * 5 + col + 1;  // Lưu vị trí ô

                // Thêm sự kiện nhấp chuột vào từng ô
                cell.setOnAction(e -> showDialog(cellNumber));

                grid.add(cell, col, row);  // Thêm ô vào lưới
            }
        }

        // Đặt lưới vào bố cục chính
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(grid);

        // Thêm vào Scene graph
        getChildren().add(layout);
    }

    // Hiển thị Dialog khi nhấp vào ô
    private void showDialog(int cellNumber) {
        // Sử dụng Alert để hiển thị thông tin
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Cell Clicked");
        dialog.setHeaderText(null);
        dialog.setContentText("You clicked on cell number: " + cellNumber);

        dialog.showAndWait();  // Hiển thị và chờ người dùng đóng dialog
    }
}

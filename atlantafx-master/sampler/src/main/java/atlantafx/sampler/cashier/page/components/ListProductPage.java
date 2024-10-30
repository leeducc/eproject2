package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.base.entity.common.Product;
import atlantafx.sampler.base.service.cashier.CashierService;
import atlantafx.sampler.base.util.AlertUtil;
import atlantafx.sampler.cashier.page.OutlinePage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ListProductPage extends OutlinePage {

    public static final String NAME = "List Product";
    List<Product> filteredProducts = new ArrayList<>();
    CashierService cf = new CashierService();
    private static File selectedFile;
    Stage primaryStage;

    @Override
    public String getName() {
        return NAME;
    }

    public ListProductPage() {
        super();
        viewProduct();
    }

    public void viewProduct() {
        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getStylesheets()
                .add(getClass().getResource("/css/coffeeMenuApp.css").toExternalForm());
        mainLayout.getStyleClass().add("root");

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Nhập tên món cần tìm");
        searchBar.setPrefWidth(300);
        searchBar.getStyleClass().add("text-field");

        Button addNewProductButton = new Button("Thêm mới đồ uống");
        addNewProductButton.getStyleClass().add("add-button");

        // ComboBox for filtering
        ComboBox<String> comboBox = CashierService.createPayCategoriesSelectionBox();
        comboBox.getItems().add("All");
        comboBox.setValue("All");
        comboBox.getStyleClass().add("combo-box");

        // Top HBox layout for search bar, search button, and ComboBox
        HBox topLayout = new HBox(10, searchBar, comboBox);
        topLayout.setPadding(new Insets(10));
        topLayout.setAlignment(Pos.CENTER);

        // Product Grid Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        filteredProducts = CashierService.getAllProducts();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim();
            filteredProducts = CashierService.getProductsByKey(searchText); // Fetch filtered products
            updateProductGrid(gridPane, filteredProducts); // Update the grid with filtered products
        });
        comboBox.setOnAction(event -> {
            String selectFilter = comboBox.getValue();
            if ("All".equals(selectFilter) || selectFilter.isEmpty() || selectFilter == null) {
                filteredProducts = CashierService.getAllProducts();
            } else {
                filteredProducts = CashierService.getProductsByCategory(selectFilter);
            }
            updateProductGrid(gridPane, filteredProducts);
        });

        addNewProductButton.setOnAction(e -> {
            Product newProduct = showNewProductDialog(primaryStage);
            if (newProduct != null) {
                CashierService.addNewProduct(newProduct);
                updateProductGrid(gridPane, filteredProducts);
                AlertUtil.showErrorAlert("Thêm Thành Công");
            } else {
                System.out.println("khong thay doi");
            }
        });
        cf.createProductGrid(filteredProducts, gridPane);

        // ScrollPane for Product Grid Layout
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Add components to the main layout, including the ScrollPane
        mainLayout.getChildren().addAll(topLayout, scrollPane, addNewProductButton);
        getChildren().add(mainLayout);
    }

    private void updateProductGrid(GridPane gridPane, List<Product> productList) {
        gridPane.getChildren().clear();
        cf.createProductGrid(productList, gridPane);
    }

    private static Product showNewProductDialog(Stage primaryStage) {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setHeaderText(null);
        dialog.getDialogPane().getStylesheets().add(
                ListProductPage.class.getResource("/css/cssDiaLogAddNewProduct.css").toExternalForm()
        );

        TextField imageLink = new TextField();
        imageLink.getStyleClass().add("dialog-text-field");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("dialog-grid-pane");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("dialog-image-view");
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Button uploadButton = new Button("Upload Image");
        uploadButton.getStyleClass().add("dialog-button");
        uploadButton.setOnAction(e -> {
            fileChooser.setTitle("Select Image");
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                imageLink.setText(selectedFile.getName());
            }
        });

        Button editButton = new Button("Edit Image");
        editButton.getStyleClass().add("dialog-button");
        editButton.setOnAction(e -> {
            fileChooser.setTitle("Select New Image");
            File newFile = fileChooser.showOpenDialog(primaryStage);
            if (newFile != null) {
                Image image = new Image(newFile.toURI().toString());
                imageView.setImage(image);
            }
        });

        TextField category = new TextField();
        category.getStyleClass().add("dialog-text-field");

        TextField name = new TextField();
        name.getStyleClass().add("dialog-text-field");

        TextField price = new TextField();
        price.getStyleClass().add("dialog-text-field");

        grid.add(new Label("Ảnh"), 0, 0);
        grid.add(imageView, 1, 0);
        grid.add(uploadButton, 2, 0);
        grid.add(editButton, 3, 0);
        grid.add(new Label("Loại Đồ Uống: "), 0, 1);
        grid.add(category, 1, 1);
        grid.add(new Label("Tên Đồ Uống"), 0, 2);
        grid.add(name, 1, 2);
        grid.add(new Label("Giá"), 0, 3);
        grid.add(price, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                // Kiểm tra xem category, name và price có rỗng không
                if (category.getText() == null || category.getText().isEmpty() ||
                        name.getText() == null || name.getText().isEmpty() ||
                        price.getText() == null || price.getText().isEmpty()) {
                    AlertUtil.showErrorAlert("Vui lòng nhập đầy đủ thông tin.");
                    return null; // Trả về null để giữ nguyên dialog
                }

                try {
                    // Chuyển đổi giá từ String sang Double
                    double parsedPrice = Double.parseDouble(price.getText());

                    // Kiểm tra xem selectedFile có hợp lệ không
                    if (selectedFile != null && selectedFile.exists()) {
                        File destinationFile = new File(
                                "sampler/src/main/resources/images/products/" + selectedFile.getName());
                        // Sao chép tệp
                        Files.copy(selectedFile.toPath(), destinationFile.toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        AlertUtil.showErrorAlert("Tệp không hợp lệ.");
                        return null; // Trả về null để giữ nguyên dialog
                    }

                    // Trả về đối tượng Products mới
                    return new Product(
                            "/images/products/" + selectedFile.getName(),
                            name.getText(),
                            parsedPrice,
                            Integer.parseInt(category.getText())
                    );

                } catch (NumberFormatException e) {
                    AlertUtil.showErrorAlert("Giá không hợp lệ.");
                    return null; // Trả về null để giữ nguyên dialog
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    AlertUtil.showErrorAlert("Lỗi khi sao chép tệp.");
                    return null; // Trả về null để giữ nguyên dialog
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}

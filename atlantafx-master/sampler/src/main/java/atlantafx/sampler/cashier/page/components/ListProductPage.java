package atlantafx.sampler.cashier.page.components;

import atlantafx.sampler.base.entity.common.Products;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
  List<Products> filteredProducts = new ArrayList<>();
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
    mainLayout.getStyleClass().add("root"); // Apply background style from CSS

    // Search Bar
    TextField searchBar = new TextField();
    searchBar.setPromptText("Nhập mã/Tên món cần tìm");
    searchBar.setPrefWidth(300);
    searchBar.getStyleClass().add("text-field");

    Button addNewProductButton = new Button("Thêm mới đồ uống");
//    Button refresh = new Button("Làm mới");

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
      Products newProduct = showNewProductDialog(primaryStage);
      if (newProduct != null) {
        CashierService.addNewProduct(newProduct);
        System.out.println("them san pham thanh cong");
      } else {
        System.out.println("khong thay doi");
      }

    });
    cf.createProductGrid(filteredProducts, gridPane);

    // ScrollPane for Product Grid Layout
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(gridPane);
    scrollPane.setFitToWidth(true); // Makes sure the content adjusts to the width of the ScrollPane
    scrollPane.setPannable(true); // Allows panning
    scrollPane.setVbarPolicy(
        ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar when needed
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scrollbar

    // Add components to the main layout, including the ScrollPane
    mainLayout.getChildren().addAll(topLayout, scrollPane, addNewProductButton);
    getChildren().add(mainLayout);
  }

  private void updateProductGrid(GridPane gridPane, List<Products> productList) {
    gridPane.getChildren().clear(); // Clear existing items in the grid
    cf.createProductGrid(productList, gridPane);
  }

  private static Products showNewProductDialog(Stage primaryStage) {
    Dialog<Products> dialog = new Dialog<>();
    dialog.setHeaderText(null);
    TextField imageLink = new TextField();
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

    ImageView imageView = new ImageView();
    imageView.setFitWidth(200); // Set width
    imageView.setFitHeight(200); // Set height
    imageView.setPreserveRatio(true); // Preserve aspect ratio

    Button uploadButton = new Button("Tải 1 ảnh Lên");
    uploadButton.setOnAction(e -> {
      fileChooser.setTitle("Select Image");
      if (selectedFile != null) {
        // Load image
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
        // Update the image link text field
        imageLink.setText(selectedFile.getName()); // Only the file name, adjust as needed
      } else {
        AlertUtil.showErrorAlert("Vui lòng chọn ảnh");
      }
    });

    Button editButton = new Button("Chọn ảnh khác");
    editButton.setOnAction(e -> {
      if (selectedFile != null) {
        fileChooser.setTitle("Select New Image");
        File newFile = fileChooser.showOpenDialog(primaryStage);
        if (newFile != null) {
          // Load new image
          Image image = new Image(newFile.toURI().toString());
          imageView.setImage(image);
          imageLink.setText(newFile.getName());
        } else {
          AlertUtil.showErrorAlert("Vui lòng chọn ảnh");
        }
      } else {
        AlertUtil.showErrorAlert("Không có ảnh nào được chọn trước đó");
      }
    });

    TextField category = new TextField("");
    TextField name = new TextField("");
    TextField price = new TextField("");

    grid.add(new Label("Ảnh"), 0, 0);
    grid.add(imageView, 1, 0);
    grid.add(uploadButton, 2, 0);
    grid.add(editButton, 3, 0);
    grid.add(new Label("Loại Đồ Uống: "), 0, 1);
    grid.add(category, 1, 1);
    grid.add(new Label("Tên Đồ Uống"), 0, 2);
    grid.add(name, 1, 2);
    grid.add(new Label("Gía"), 0, 3);
    grid.add(price, 1, 3);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(button -> {
      if (button == ButtonType.OK) {
        // Save the image to the project folder
        try {
          // Update the path according to your project structure
          File destinationFile = new File(
              "sampler/src/main/resources/images/products/" + selectedFile.getName());

          Files.copy(selectedFile.toPath(), destinationFile.toPath(),
              StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }

        return new Products(
            "/images/products/" + selectedFile.getName(), // Update with the correct path
            category.getText(),
            name.getText(),
            Double.parseDouble(price.getText())
        );
      } else if (button == ButtonType.CANCEL) {
        return null;
      }
      return null;
    });
    return dialog.showAndWait().orElse(null);
  }

}

//package atlantafx.sampler.admin.page.dialog;
//
//import atlantafx.sampler.admin.layout.ModalDialog;
//import atlantafx.sampler.base.entity.common.Category;
//import atlantafx.sampler.base.entity.common.Products;
//import atlantafx.sampler.base.service.cashier.CashierService;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.*;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.util.List;
//
//public class AddProductDialog extends ModalDialog {
//
//    private final TextField imageLinkField = new TextField();
//    private final TextField nameField = new TextField();
//    private final TextField priceField = new TextField();
//    private final ComboBox<Category> categoryComboBox = new ComboBox<>();
//    private final CashierService cashierService = new CashierService();
//    private final FileChooser fileChooser = new FileChooser(); // File chooser for image upload
//
//
//
//    public AddProductDialog() {
//        super();
//        header.setTitle("Add New Product");
//        content.setBody(createContent());
//        content.setFooter(createFooter());
//        content.setPrefSize(400, 300);
//        populateCategories(); // Load categories when dialog is created
//
//        // Configure the file chooser
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
//    }
//
//    private VBox createContent() {
//        Label imageLinkLabel = new Label("Image Link:");
//        Label nameLabel = new Label("Product Name:");
//        Label priceLabel = new Label("Price:");
//        Label categoryLabel = new Label("Category:");
//
//        // Button to upload image
//        Button uploadButton = new Button("Upload Image");
//        uploadButton.setOnAction(event -> uploadImage());
//
//        VBox content = new VBox(10, imageLinkLabel, imageLinkField,
//                uploadButton, // Add upload button
//                nameLabel, nameField,
//                priceLabel, priceField,
//                categoryLabel, categoryComboBox);
//        content.setPadding(new Insets(20));
//        content.setAlignment(Pos.CENTER);
//        return content;
//    }
//
//    private void uploadImage() {
//        File file = fileChooser.showOpenDialog(getScene().getWindow());
//        if (file != null) {
//            // Define the absolute path to the target directory
//            String targetImageDir = "C:\\Users\\Anonymous\\IdeaProjects\\project\\atlantafx-master\\eproject2\\atlantafx-master\\sampler\\src\\main\\resources\\images\\products";
//            File targetDir = new File(targetImageDir);
//
//            // Ensure the target directory exists
//            if (!targetDir.exists()) {
//                if (targetDir.mkdirs()) {
//                    System.out.println("Directory created: " + targetDir.getAbsolutePath());
//                } else {
//                    System.out.println("Failed to create directory: " + targetDir.getAbsolutePath());
//                }
//            }
//
//            try {
//                // Create the target file path
//                String fileName = file.getName();
//                File targetFile = new File(targetDir, fileName); // Specify the target file
//
//                // Copy the file to the target directory
//                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//                // Set the image link field to match SQL format
//                String categoryId = categoryComboBox.getValue() != null ? String.valueOf(categoryComboBox.getValue().getId()) : "0";
//                imageLinkField.setText(categoryId + ", " + "/images/products/" + fileName);
//                System.out.println("Image uploaded successfully: " + targetFile.getAbsolutePath());
//            } catch (IOException e) {
//                // Show detailed error message
//                showAlert("File Error", "Failed to upload the image: " + e.getMessage());
//                e.printStackTrace(); // Print stack trace for debugging
//            }
//        }
//    }
//
//    private HBox createFooter() {
//        Button saveButton = new Button("Save");
//        saveButton.setOnAction(event -> {
//            try {
//                // Collect product data
//                Products product = new Products();
//                String[] imageLinkParts = imageLinkField.getText().split(", "); // Adjusting the parsing logic
//                if (imageLinkParts.length == 2) {
//                    product.setImageLink(imageLinkParts[1].trim()); // Store the path part and trim whitespace
//                    product.setName(nameField.getText());
//                    product.setPrice(Double.parseDouble(priceField.getText()));
//                    product.setCategoryId(Integer.parseInt(imageLinkParts[0].trim())); // Extract and trim ID
//                } else {
//                    showAlert("Input Error", "Please upload an image first.");
//                    return;
//                }
//
//                if (cashierService.addProduct(product)) {
//                    close(); // Close dialog on success
//                } else {
//                    showAlert("Error", "Failed to add product.");
//                }
//            } catch (NumberFormatException e) {
//                showAlert("Input Error", "Please enter a valid price.");
//            }
//        });
//
//        Button cancelButton = new Button("Cancel");
//        cancelButton.setOnAction(event -> close());
//
//        return new HBox(10, saveButton, cancelButton);
//    }
//    private void populateCategories() {
//        List<Category> categories = cashierService.loadCategories();
//        categoryComboBox.getItems().addAll(categories);
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}

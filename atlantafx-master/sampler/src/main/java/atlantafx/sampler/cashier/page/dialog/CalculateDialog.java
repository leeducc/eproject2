package atlantafx.sampler.cashier.page.dialog;

import atlantafx.sampler.cashier.layout.ModalDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import atlantafx.base.theme.Styles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CalculateDialog extends ModalDialog {

    private final TableDialog parentDialog;

    public CalculateDialog(double totalAmount, TableDialog parentDialog) {
        super();
        this.parentDialog = parentDialog;
        setId("calculate-dialog");
        header.setTitle("Calculate Total");
        content.setBody(createContent(totalAmount));
        content.setFooter(createFooter());
        content.setPrefSize(400, 300);

        // Add a visible border to the content for better visual clarity
        content.setBorder(new Border(new BorderStroke(
                Color.BLACK, // Border color
                BorderStrokeStyle.SOLID, // Border style
                new CornerRadii(8), // Rounded corners
                new BorderWidths(2) // Border thickness
        )));
    }

    private VBox createContent(double totalAmount) {
        Label totalLabel = new Label("Total Amount: " + totalAmount);
        totalLabel.getStyleClass().add(Styles.TITLE_4);
        totalLabel.setAlignment(Pos.CENTER);

        // Payment method buttons
        HBox paymentMethods = new HBox(10);
        Button cashButton = new Button("Cash");
        cashButton.setOnAction(event -> openCashDialog());

        Button qrButton = new Button("QR");
        qrButton.setOnAction(event -> openQRDialog());

        Button cardButton = new Button("Card");
        cardButton.setOnAction(event -> openCardDialog());

        paymentMethods.getChildren().addAll(cashButton, qrButton, cardButton);
        paymentMethods.setAlignment(Pos.CENTER);

        // Voucher input field
        TextField voucherInput = new TextField();
        voucherInput.setPromptText("Enter Voucher Code");

        // Submit button for voucher
        Button submitVoucherButton = new Button("Apply Voucher");
        submitVoucherButton.setOnAction(event -> applyVoucher(voucherInput.getText()));

        VBox content = new VBox(15, totalLabel, paymentMethods, voucherInput, submitVoucherButton);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));
        return content;
    }
    private void redirectToTableDialog() {
        // Close this dialog and open the parent TableDialog
        parentDialog.show(getScene()); // Show the parent TableDialog again
        close(); // Close this dialog
    }
    private HBox createFooter() {
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> redirectToTableDialog());

        HBox footer = new HBox(closeButton);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        return footer;
    }

    private void openCashDialog() {
        // Open Cash Payment Dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cash Payment");
        alert.setHeaderText("You selected Cash payment.");
        alert.setContentText("Process cash payment here.");
        alert.showAndWait();
    }

    private void openQRDialog() {
        // Open QR Payment Dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("QR Payment");
        alert.setHeaderText("You selected QR payment.");
        alert.setContentText("Process QR payment here.");
        alert.showAndWait();
    }

    private void openCardDialog() {
        // Open Card Payment Dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card Payment");
        alert.setHeaderText("You selected Card payment.");
        alert.setContentText("Process card payment here.");
        alert.showAndWait();
    }

    private void applyVoucher(String voucherCode) {
        // Logic to validate the voucher and apply it to the total
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Voucher Application");

        // For demonstration, assume any non-empty voucher is valid
        if (!voucherCode.isEmpty()) {
            alert.setHeaderText("Voucher Applied");
            alert.setContentText("Voucher code " + voucherCode + " has been applied.");
        } else {
            alert.setHeaderText("Invalid Voucher");
            alert.setContentText("Please enter a valid voucher code.");
        }

        alert.showAndWait();
    }
}

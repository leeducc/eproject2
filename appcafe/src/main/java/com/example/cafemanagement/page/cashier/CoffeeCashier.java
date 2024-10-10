package com.example.cafemanagement.page.cashier;

import com.example.cafemanagement.page.cashier.CashierHomePage;
import javafx.application.Application;
import javafx.stage.Stage;

public class CoffeeCashier extends Application {

    CashierHomePage cashierHomePage = new CashierHomePage();

    @Override
    public void start(Stage primaryStage) {
        cashierHomePage.cashierHomePage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

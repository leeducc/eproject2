package com.example.cafemanagemnet;


import com.example.cafemanagemnet.page.PageLogin;
import javafx.application.Application;
import javafx.stage.Stage;


public class CoffeeLogin extends Application {

  PageLogin pageLogin = new PageLogin();

  @Override
  public void start(Stage primaryStage) {
    pageLogin.pageLogin(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


package com.example.cafemanagement;

import com.example.cafemanagement.controller.PageLoginController;
//import com.example.cafemanagement.page.PageLogin;
import javafx.application.Application;
import javafx.stage.Stage;


public class CoffeeLogin extends Application {

  PageLoginController pageLogin = new PageLoginController();

  @Override
  public void start(Stage primaryStage) {
    pageLogin.pageLogin(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


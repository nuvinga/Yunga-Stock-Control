package stockControlWithFxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        Login();
    }

    public void Login() throws Exception {
        Stage loginStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        loginStage.setScene(new Scene(root, 541, 673));
        loginStage.setTitle("Automated Stock Control System - Login");
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.show();
    }

}

package stockControlWithFxml.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stockControlWithFxml.StaffVerifier;

import java.sql.*;
import java.time.LocalDate;

public class LoginController {

    @FXML
    private Button signInButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    private final LocalDate date = LocalDate.now();

    public void signIn() throws Exception{

        Alert errorPrompt = new Alert(Alert.AlertType.WARNING);
        Stage errorStage = (Stage) errorPrompt.getDialogPane().getScene().getWindow();
        errorStage.getIcons().add(new Image("/redError.png"));
        errorPrompt.setGraphic(new ImageView(this.getClass().getResource("/redError.png").toString()));
        errorPrompt.setTitle("Oops! That's an Error");
        errorPrompt.setHeaderText("That's Odd!");

        if (!usernameInput.getText().equals("") && !passwordInput.getText().equals("")) {
            String username = usernameInput.getText().trim();
            String password = passwordInput.getText().trim();

            boolean found = false;
            boolean usernameFound = false;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wigynsa?useTimezone=true&serverTimezone=UTC", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `staff`");
//            statement.executeUpdate("SELECT * FROM `staff`");   // to create a table or add fields
            String verificationKey = null;
            while (resultSet.next() && !found) {
                StaffVerifier verify = new StaffVerifier(resultSet);                                      
                verificationKey = verify.getVerification(username, password);
                //else switch gate
                // gate key 223.2j[qa
                switch (verificationKey) {
                    case "grant-top": {
                        //Dashboard Stage Initialization and Out
                        Stage dashboard = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("..//topDashboard//topDashboard.fxml"));
                        dashboard.setScene(new Scene(root));
                        dashboard.setMaximized(true);
                        dashboard.initStyle(StageStyle.UTILITY);
                        dashboard.setTitle("Yunga Tech's Automated Stock Control System - Dashboard");
                        dashboard.show();
                        found=true;
                        usernameFound=true;
                        Stage loginStage = (Stage) usernameInput.getScene().getWindow();
                        loginStage.close();
                        break;
                    }
                    case "grant-low": {
                        //Dashboard Stage Initialization and Out
                        Stage dashboard = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("..//lowDashboard//lowDashboard.fxml"));
                        dashboard.setScene(new Scene(root));
                        dashboard.setMaximized(true);
                        dashboard.initStyle(StageStyle.UTILITY);
                        dashboard.setTitle("Yunga Tech's Automated Stock Control System - Dashboard");
                        dashboard.show();
                        found=true;
                        usernameFound=true;
                        Stage loginStage = (Stage) usernameInput.getScene().getWindow();
                        loginStage.close();
                        break;
                    }
                    case "grant-null":
                        errorPrompt.setContentText("Oops! Seems like your account doesn't have permission to the" +
                                " Stock Control System! \nSpeak to your Technical Team for more info. \n" +
                                "Error Ref #: " + date + "/221");
                        errorPrompt.show();
                        found=true;
                        usernameFound=true;
                        break;
                    case "error-password":
                        usernameFound=true;
                        break;
                }
            }
            if (!usernameFound) {
                errorPrompt.setContentText("Oops! That username doesn't seem to be a Wigynsa Account!\n" +
                        "Please check your Username again. \nSpeak to your Technical team for more info. \n" +
                        "Error Ref #: " + date + "/223");
                errorPrompt.show();
            }else if (!found){
                errorPrompt.setContentText("Oops! That password doesn't seem to work on your account! \n" +
                            "Please check your password again. \n Speak to your Technical team for more info. \n" +
                            "Error Ref #: " + date + "/223");
                errorPrompt.show();
            }
        }else{
            errorPrompt.setContentText("User name and password fields need to be completed");
            errorPrompt.show();
        }
    }
}
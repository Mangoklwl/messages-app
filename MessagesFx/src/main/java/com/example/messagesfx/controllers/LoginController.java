package com.example.messagesfx.controllers;

import com.example.messagesfx.ScreenLoader;
import com.example.messagesfx.responses.LoginResponse;
import com.example.messagesfx.services.LoginService;
import com.example.messagesfx.services.ServiceUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
  //  @FXML
 //   private Button btnlogin;

    @FXML
    private Label lblpassword;

    @FXML
    private Label lbluser;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtuser;

    @FXML
    private Label lbltest;


    //-----funcion del login----

    @FXML
    void onHelloButtonClick(ActionEvent event) throws IOException {

        LoginService lss = new LoginService(txtuser.getText(), txtpassword.getText());
        lss.start();
        lss.setOnSucceeded(
                e -> {
                    LoginResponse response = lss.getValue();

                    try{
                       // System.out.println("RESPUESTA: " + response.isOk());
                        if (response.ok) {
                            ServiceUtils.setToken(response.token);// metemos en la clase serviceutils el token
                            ServiceUtils.setImageB64(response.image);// metemos en la clase serviceutils la imagen
                            ServiceUtils.setNombre(response.name);
                            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            try {
                                ScreenLoader.loadScreen("unavezregistrado.fxml",appStage);
                                //ScreenLoader.loadScreenWithUserImg("unavezregistrado.fxml",appStage,response.image);
                            } catch(IOException ex) { throw new RuntimeException(ex); }

                        }
                    }catch (Exception ex){

                        Alert dialogo = new Alert((Alert.AlertType.INFORMATION));
                        dialogo.setTitle(("Error"));
                        dialogo.setContentText("Usuario o contrasela incorrectos");
                        dialogo.initStyle(StageStyle.UTILITY);
                        dialogo.showAndWait();
                    }

                }
        );

                lss.setOnFailed(e->{
                    //poner un label con un mensaje de error
                    System.out.println("asadfasdfasdf");
                });

    }

    @FXML void onHyper(ActionEvent event) throws IOException {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ScreenLoader.loadScreen("registro-view.fxml", appStage);

//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("registro-view.fxml")));
//        Scene scene = new Scene(root);
//        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        appStage.setScene(scene);
//        appStage.toFront();
//        appStage.show();

    }

 //  ----funcion que sacaria una lista de usuarios-----
//    @FXML
//    protected void onUserButtonClick(ActionEvent event) {
//        UsuariosListservice listausers= new UsuariosListservice();
//        listausers.start();
//
//        listausers.setOnSucceeded(
//                e -> {
//                    Users[] response= listausers.getValue();
//                }
//        );
//    }

}
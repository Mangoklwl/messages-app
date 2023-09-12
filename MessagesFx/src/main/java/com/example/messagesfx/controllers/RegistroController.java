package com.example.messagesfx.controllers;

import com.example.messagesfx.ScreenLoader;
import com.example.messagesfx.responses.LoginResponse;
import com.example.messagesfx.responses.RegistroResponse;
import com.example.messagesfx.services.LoginService;
import com.example.messagesfx.services.RegistroService;
import com.example.messagesfx.services.ServiceUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
public class RegistroController {
    @FXML
    private TextField txtuserregistro;
    @FXML
    private TextField txtpasswordregistro;
    @FXML
    private TextField txtrepetircontraseña;


    @FXML
    private ImageView imagen;
    @FXML
    private Label lblAlerta;


    @FXML
    void onregister(ActionEvent event) throws IOException {
        if (checkField()) {

            String urlImage = imagen.getImage().getUrl().replace("file:/", "");
            System.out.println("LA URL: " + urlImage);
            String imageB64 = ServiceUtils.transformImage(urlImage);
            RegistroService lss = new RegistroService(txtuserregistro.getText(), txtpasswordregistro.getText(), imageB64);
            lss.start();
            lss.setOnSucceeded(
                    e -> {
                        RegistroResponse response = lss.getValue();

                        if (response.code == 200) {
                            Alert dialogo = new Alert((Alert.AlertType.INFORMATION));
                            dialogo.setTitle((""));
                            dialogo.setContentText("Se ha registrado correctamente");
                            dialogo.initStyle(StageStyle.UTILITY);
                            dialogo.showAndWait();
                            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                ScreenLoader.loadScreen("hello-view.fxml", appStage);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        }

                    }
            );
        }


    }
    @FXML
    void onImageSelect (ActionEvent event) throws IOException {


        // clase para abrir el explorador de archivos
        FileChooser fileChooser = new FileChooser();

            //filtros para que solo puedas poner esas extensiones
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

        //añadir al filechooser los filtros
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // abre el explorador, la función 'showOpenDialog' devuelve un fichero que se guarda en la variable 'file'
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imagen.setImage(image);
//            System.out.println("FILE TO URI: " + file.toURI().toString());
//            System.out.println("La imagen: " + imagen.toString());
//            System.out.println("La imagen2: " + imagen.getImage().toString());
//            System.out.println("La imagen3: " + imagen.getImage().getUrl());

        }

    }

    boolean checkField() {

//            System.out.println("Entra en chefield");
//            System.out.println("User: " + txtuserregistro.getText().isEmpty());
//            System.out.println("Pass1: " + txtpasswordregistro.getText().isEmpty());
//            System.out.println("Pass2: " + txtrepetircontraseña.getText().isEmpty());
        boolean bien = true;
        lblAlerta.setText("");
        if (txtuserregistro.getText().isEmpty() || txtpasswordregistro.getText().isEmpty() || txtrepetircontraseña.getText().isEmpty()) {
            System.out.println("AQUI DEBERÍA ENTRAR");
            Alert dialogo = new Alert((Alert.AlertType.INFORMATION));
            dialogo.setTitle(("Error"));
            dialogo.setContentText("No puede haber un campo vacio");
            dialogo.initStyle(StageStyle.UTILITY);
            dialogo.showAndWait();
            bien = false;
        }
        if (!txtpasswordregistro.getText().equals(txtrepetircontraseña.getText())) {
            lblAlerta.setText("Error, las contraseñas no coinciden");
            bien = false;

        }

        return bien;
    }








}

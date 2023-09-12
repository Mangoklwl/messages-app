package com.example.messagesfx.controllers;

import com.example.messagesfx.models.Messages;
import com.example.messagesfx.models.Users;
import com.example.messagesfx.responses.MessageResponse;
import com.example.messagesfx.responses.Response;
import com.example.messagesfx.responses.UsersResponse;
import com.example.messagesfx.services.MessagesService;
import com.example.messagesfx.services.ServiceUtils;
import com.example.messagesfx.services.UserUpdateService;
import com.example.messagesfx.services.UsersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.awt.image.BufferedImage;
import java.util.List;

public class MessagesControler {

    private ObservableList<Messages> messageList;

    private ObservableList<Users> usurlist;
    @FXML
    private ImageView imagen;
    @FXML
    private TableView<Messages> tablamensaje;
    @FXML
    private TableView<Users> tablamandarmensaje;
    @FXML
    private TableColumn<Users, String> name;
    @FXML
    private TableColumn<Users, ImageView> image2;
    @FXML
    private TableColumn<Messages, String> message;
    @FXML
    private TableColumn<Messages, ImageView> image;
    @FXML
    private TableColumn<Messages, String> sent;


    @FXML
    private Button btnrefrescar;
    @FXML
    private Label lblnombreusu;


    public void initialize() {
        lblnombreusu.setText(ServiceUtils.getNombre());

        //btnrefrescar.fire();
        loadProfileImage();
        getMessages();
        getUsuarios();


    }

    @FXML
    public void refreshPage(ActionEvent event) throws IOException {
        System.out.println("SE LANZA EL EVENTO DEL BOTON");
        getMessages();
        getUsuarios();
    }
    @FXML
    void cambiarImagenPerfil(ActionEvent event) throws IOException {

        // Cogemos la imagen del file explorer
        File image = getImageFromFile();

        // La convertimos a base64
        String urlImage = imagen.getImage().getUrl().replace("file:/", "");
        String imgB64 = ServiceUtils.transformImage(urlImage);

        // La subimos al servidor.
        UserUpdateService lss = new UserUpdateService(imgB64);
        lss.start();
        lss.setOnSucceeded(e->{
            Response response = lss.getValue();
            if(response.ok) {
                Alert dialogo = new Alert((Alert.AlertType.INFORMATION));
                dialogo.setTitle((""));
                dialogo.setContentText("Imagen cambiada correctamente");
                dialogo.initStyle(StageStyle.UTILITY);
                dialogo.showAndWait();
            }
        });
    }

    File getImageFromFile() {
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
        }
        return file;
    }

    void getMessages() {
        MessagesService lss = new MessagesService();
        lss.start();
        lss.setOnSucceeded(e->{
            MessageResponse response = lss.getValue();

            if(response != null && response.ok) {
                //System.out.println("LOS MENSAJES SALEN");
                tablaMensaje(response.messages);
            }

        });
    }



    void tablaMensaje(List<Messages> dataList){
        try{
            messageList = FXCollections.observableArrayList(dataList);
            //messageList.add(new Messages("test","test", "test","test","test","test"));
            message.setCellValueFactory(new PropertyValueFactory("message"));
            //image.setCellValueFactory(new PropertyValueFactory("image"));
            image.setCellValueFactory(new PropertyValueFactory("image"));
            sent.setCellValueFactory(new PropertyValueFactory("sent"));
            tablamensaje.setItems(messageList);
        }catch (Exception e){
            System.out.println("error en funcion tablamensajes");
        }


    }
    void getUsuarios(){
        UsersService lss= new UsersService();
        lss.start();
        lss.setOnSucceeded(e->{
            UsersResponse response=lss.getValue();
            if( response!=null && response.ok){
                tablaUsuarios(response.users);

            }
        });

    }

    void tablaUsuarios(List<Users> users) {
        try{
            usurlist = FXCollections.observableArrayList(users);
            name.setCellValueFactory(new PropertyValueFactory("name"));
            image2.setCellValueFactory(new PropertyValueFactory("image"));
            tablamandarmensaje.setItems(usurlist);
        }catch (Exception e){
            System.out.println("error en funcion tabla mandar mensaje");
        }

    }

    void loadProfileImage() {
        File file;
        try {
           file = ServiceUtils.fromB64ToImage(ServiceUtils.getImageB64(), "userImage.jpg");
        } catch (IOException e) { throw new RuntimeException(e); }

        Image image = new Image(file.toURI().toString());
        imagen.setImage(image);
    }




}

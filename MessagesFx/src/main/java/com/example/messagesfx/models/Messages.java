package com.example.messagesfx.models;

import com.example.messagesfx.services.ServiceUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Messages {
    public String id;
    public String from;
    public String to;
    public String message;
    public String image;
    public String sent;


    public Messages(String id, String from, String to, String message, String image, String sent) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.image = image;
        this.sent = sent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public ImageView getImageView() {
        ImageView imgView = null;
        File resFile = new File("src/main/resources/images/userImage");
        Image image = new Image(resFile.toURI().toString());
        imgView.setImage(image);
        imgView.setFitHeight(30);
        imgView.setPreserveRatio(true);
        return imgView;
    }

}

package com.example.messagesfx.models;

public class Users {

    public String _id;
    public String name;
    public String password;
    public String image;

    public Users(String _id, String name, String password, String image) {
        this._id = _id;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

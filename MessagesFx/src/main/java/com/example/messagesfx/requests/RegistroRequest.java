package com.example.messagesfx.requests;

public class RegistroRequest {
   public  String name;
   public String password;
   public String image;

    public RegistroRequest(String name, String password, String image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
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
}

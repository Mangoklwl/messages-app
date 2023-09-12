package com.example.messagesfx.responses;

public class LoginResponse extends Response  {
    public  String token;
    public String image;
    public String name;

    public LoginResponse(String token, String image, String name) {
        this.token = token;
        this.image = image;
        this.name = name;
    }
}

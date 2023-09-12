package com.example.messagesfx.responses;

public class RegistroResponse extends Response  {
    public  int code;
    public String id;
    public String image;
    public String password;


    public RegistroResponse(int code, String id, String image, String password) {
        this.code = code;
        this.id = id;
        this.image = image;
        this.password = password;
    }
}

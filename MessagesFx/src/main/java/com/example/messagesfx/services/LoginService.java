package com.example.messagesfx.services;

import com.example.messagesfx.requests.LoginRequest;
import com.example.messagesfx.responses.LoginResponse;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<LoginResponse> {

    public String name;
    public String password;

    public LoginService(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    protected Task<LoginResponse> createTask() {
        return new Task<LoginResponse>() {
            @Override
            protected LoginResponse call() throws Exception {
                System.out.println("Entra en el loginservice");
                LoginResponse result=null;

                Gson gson = new Gson();
                String Resquest= gson.toJson(new LoginRequest(name, password));

                String resultado= ServiceUtils.getResponse(
                        "http://localhost:8080/login",
                        Resquest,
                        "POST"
                );
                System.out.println(resultado);

                result = gson.fromJson(resultado,LoginResponse.class);
               // loginResponse.setOk(true);
                return result;
            }
        };
    }
}

package com.example.messagesfx.services;

import com.example.messagesfx.requests.LoginRequest;
import com.example.messagesfx.requests.RegistroRequest;
import com.example.messagesfx.responses.LoginResponse;
import com.example.messagesfx.responses.RegistroResponse;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RegistroService extends Service<RegistroResponse> {

   public String name;
    public String password;
    public String image;

    public RegistroService(String name, String password, String image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }

    @Override
    protected Task<RegistroResponse> createTask() {
        return new Task<RegistroResponse>() {
            @Override
            protected RegistroResponse call() throws Exception {
                System.out.println("Entra en el registroservice");
                RegistroResponse result=null;

                Gson gson = new Gson();
                String Resquest= gson.toJson(new RegistroRequest(name,password,image));

                String resultado= ServiceUtils.getResponse(
                        "http://localhost:8080/registro",
                        Resquest,
                        "POST"
                );
                System.out.println(resultado);

                result = gson.fromJson(resultado,RegistroResponse.class);
                return result;
            }
        };
    }
}

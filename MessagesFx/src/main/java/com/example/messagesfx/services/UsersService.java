package com.example.messagesfx.services;

import com.example.messagesfx.responses.UsersResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UsersService extends Service<UsersResponse> {

    @Override
    protected Task<UsersResponse> createTask() {
        return  new Task<UsersResponse>() {
            @Override
            protected UsersResponse call() throws Exception {
                UsersResponse result=null;
                Gson gson = new Gson();
                String  resultado= ServiceUtils.getResponse(
                        "http://localhost:8080/buscarusuarios",
                        null,
                        "GET"
                );
                System.out.println(resultado);
                result=gson.fromJson(resultado,UsersResponse.class);
                return result;
            }
        };
    }
}

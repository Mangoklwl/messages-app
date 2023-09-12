package com.example.messagesfx.services;

import com.example.messagesfx.responses.LoginResponse;
import com.example.messagesfx.responses.MessageResponse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import com.google.gson.Gson;


public class MessagesService extends Service<MessageResponse> {


    @Override
    protected Task<MessageResponse> createTask() {
        return new Task<MessageResponse>() {
            @Override
            protected MessageResponse call() throws Exception {
                MessageResponse result = null;
                Gson gson = new Gson();

                String resultado = ServiceUtils.getResponse(
                        "http://localhost:8080/messages",
                        null,
                        "GET"
                );
                System.out.println(resultado);
                result = gson.fromJson(resultado, MessageResponse.class);
                return result;
            }
        };
    }
}

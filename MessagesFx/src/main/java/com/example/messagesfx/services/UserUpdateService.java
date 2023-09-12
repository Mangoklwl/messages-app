package com.example.messagesfx.services;

import com.example.messagesfx.requests.ImageUpdateRequest;
import com.example.messagesfx.responses.RegistroResponse;
import com.example.messagesfx.responses.Response;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UserUpdateService extends Service<Response> {

    public String image;

    public UserUpdateService(String image) {
        this.image = image;
    }

    @Override
    protected Task<Response> createTask() {
        return new Task<Response>() {
            @Override
            protected Response call() throws Exception {
                Response res = null;
                Gson gson = new Gson();

                String Request = gson.toJson(new ImageUpdateRequest(image));

                String resultado = ServiceUtils.getResponse(
                        "http://localhost:8080/users",
                        Request,
                        "PUT"
                );

                System.out.println(resultado);

                res = gson.fromJson(resultado, RegistroResponse.class);
                return res;
            }
        };
    }
}

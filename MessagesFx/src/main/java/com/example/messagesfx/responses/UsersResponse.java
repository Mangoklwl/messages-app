package com.example.messagesfx.responses;

import com.example.messagesfx.models.Users;

import java.util.List;

public class UsersResponse extends Response{

    public List<Users> users;

    public UsersResponse(List<Users> users) {
        this.users = users;
    }
}

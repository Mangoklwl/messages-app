package com.example.messagesfx.responses;

import com.example.messagesfx.models.Messages;

import java.util.List;

public class MessageResponse {
    public boolean ok;
    public List<Messages> messages;

    public MessageResponse(boolean ok, List<Messages> messages) {
        this.ok = ok;
        this.messages = messages;
    }
}

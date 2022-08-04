package com.example.chatapi.STOMP;

import lombok.Getter;
import lombok.Setter;

@Getter
public class HelloMessage {

    private String name;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

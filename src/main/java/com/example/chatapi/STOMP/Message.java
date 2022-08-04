package com.example.chatapi.STOMP;

import lombok.Getter;

@Getter
public class Message {

	String from;
	String message;

	public Message() {
	}

	public Message(String from, String message) {
		this.from = from;
		this.message = message;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

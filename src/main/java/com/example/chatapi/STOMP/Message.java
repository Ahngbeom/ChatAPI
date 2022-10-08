package com.example.chatapi.STOMP;

import lombok.Getter;

@Getter
public class Message {

	public static final String SERVER = "Server";

	public static final String COMMON = "COMMON";
	public static final String IMPORTANT = "IMPORTANT";
	public static final String ERROR = "ERROR";
	String status;

	String from;

	String message;

	public Message(String status, String from, String message) {
		this.status = status;
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

package com.wis.demo.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserMessageBean {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAndClearMessage() {
		String messageToReturn = this.message;
		this.message = null;
		return messageToReturn;
	}

}

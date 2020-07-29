package com.cap.management.exception;

import java.util.Date;

public class ExceptionResponse extends RuntimeException {
	private Date timestamp;
	private String message;
	private String details;

	public ExceptionResponse( String message, String details) {
		super();
		this.message = message;
		this.details = details;
	}
	public ExceptionResponse(String message){
        super();
        this.message=message;
    }
	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}

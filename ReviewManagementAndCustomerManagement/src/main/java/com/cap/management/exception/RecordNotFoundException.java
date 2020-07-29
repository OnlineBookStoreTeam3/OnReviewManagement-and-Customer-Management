package com.cap.management.exception;


@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException 
{
	public RecordNotFoundException(String exception) {
        super(exception);
    }
    public RecordNotFoundException(String exception,Throwable e){
        super(exception,e);
    }
}

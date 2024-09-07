package net.javaguides.springboot.exception;

public class ResourceNotException extends RuntimeException{
    public ResourceNotException(String message){
        super(message);
    }

    public ResourceNotException(String message,Throwable cause){
        super(message,cause);
    }

}

package com.alfredsson.demo.exception;

public class InvalidPasswordOrUsernameException extends RuntimeException {
    private String username;

    public InvalidPasswordOrUsernameException(String message, String username) {
        super(message);

        this.username = username;
    }


    public String getUsername() {
        return username;
    }
}
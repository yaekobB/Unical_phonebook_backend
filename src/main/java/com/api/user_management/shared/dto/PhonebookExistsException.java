package com.api.user_management.shared.dto;

public class PhonebookExistsException extends RuntimeException{
    public PhonebookExistsException(String msg) {
        super(msg);
    }
}

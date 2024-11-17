package com.api.user_management.shared.dto;

public class PhonebookNotFoundException extends RuntimeException{
    public PhonebookNotFoundException(String msg) {
        super(msg);
    }
}

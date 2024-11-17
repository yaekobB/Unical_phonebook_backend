package com.api.user_management.shared.dto;

public class PhonebookInternalServerException extends RuntimeException{
    public PhonebookInternalServerException(String msg) {
        super(msg);
    }
}

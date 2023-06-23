package com.czmp.collections.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CannotSendMessageException extends Exception {
    public CannotSendMessageException(String message){
        super(message);
    }
}

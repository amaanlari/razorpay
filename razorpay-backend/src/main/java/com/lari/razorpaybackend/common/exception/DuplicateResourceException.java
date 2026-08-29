package com.lari.razorpaybackend.common.exception;

import com.lari.razorpaybackend.common.exception.handler.ErrorCode;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}

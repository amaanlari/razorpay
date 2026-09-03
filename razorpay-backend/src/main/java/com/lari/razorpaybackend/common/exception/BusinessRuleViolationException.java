package com.lari.razorpaybackend.common.exception;

import com.lari.razorpaybackend.common.exception.handler.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessRuleViolationException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessRuleViolationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}

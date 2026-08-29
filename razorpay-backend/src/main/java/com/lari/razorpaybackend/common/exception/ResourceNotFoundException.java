package com.lari.razorpaybackend.common.exception;

import lombok.Getter;

import java.text.MessageFormat;

import com.lari.razorpaybackend.common.exception.handler.ErrorCode;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String code;

    public ResourceNotFoundException(
            ErrorCode errorCode,
            String resourceName,
            String identifierType,
            String identifierValue) {

        super(MessageFormat.format(
                errorCode.getDescription(),
                resourceName,
                identifierType,
                identifierValue
        ));

        this.errorCode = errorCode;
        this.code = MessageFormat.format(errorCode.getCode(), resourceName.toUpperCase());
    }
}

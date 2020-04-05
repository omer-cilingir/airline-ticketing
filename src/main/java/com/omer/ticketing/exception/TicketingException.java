package com.omer.ticketing.exception;

import lombok.Getter;

@Getter
public class TicketingException extends RuntimeException {
    private final Integer code;
    private final String message;

    public TicketingException(ErrorEnum errorEnum) {
        super();
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();

    }

    public TicketingException(ErrorEnum errorEnum, Throwable cause) {
        super(cause);
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }
}

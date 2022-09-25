package ru.itmo.monsters.conroller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ErrorMessage {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorMessage(HttpStatus httpStatus, String message, String path) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path.substring(path.indexOf("/"));
    }
}

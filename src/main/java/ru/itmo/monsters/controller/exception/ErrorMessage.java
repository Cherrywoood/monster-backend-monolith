package ru.itmo.monsters.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Data
public class ErrorMessage {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;

    private List<Violation> violations;
    private String path;

    public ErrorMessage(HttpStatus httpStatus, String message, String path) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path.substring(path.indexOf("/"));
    }

    public ErrorMessage(HttpStatus httpStatus, String message, String path, List<Violation> violations) {
        this(httpStatus, message, path);
        this.violations = violations;
    }
}

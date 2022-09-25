package ru.itmo.monsters.conroller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;


@RestControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<Violation> violations = ex.getConstraintViolations()
                .stream()
                .map(violation ->
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage())
                )
                .toList();

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "invalid data",
                request.getDescription(false),
                violations
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<Violation> violations = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        new Violation(fieldError.getField(), fieldError.getDefaultMessage())
                )
                .toList();

        return new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "invalid data",
                request.getDescription(false),
                violations
        );
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleSQLDataException(SQLException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage().split("\\r?\\n")[0].replaceFirst("ERROR: ", ""),
                request.getDescription(false)
        );
    }
}

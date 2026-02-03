package org.radon.pushup.shared.aop.exceptionHandling;

import org.radon.pushup.shared.dto.ErrorResponse;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionModel extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public ExceptionModel(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ResponseEntity<ErrorResponse> makeResponse(String path){
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(
                httpStatus.value(),
                httpStatus.name(),
                getMessage(),
                path
        ));
    }

}

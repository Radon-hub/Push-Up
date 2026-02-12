package org.radon.pushup.shared.aop.exceptionHandling.advice;

import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.radon.pushup.shared.dto.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionModel.class)
    public ResponseEntity<ErrorResponse> handleException(ExceptionModel model,HttpServletRequest request) {
        return model.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException model, HttpServletRequest request) {
            return new ExceptionModel(
                    model.getMessage(),
                    HttpStatus.UNAUTHORIZED
            ).makeResponse(request.getRequestURI());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleExceptionGlobally(Exception model,HttpServletRequest request) {
//        return new ExceptionModel(
//                model.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        ).makeResponse(request.getRequestURI());
//    }

}

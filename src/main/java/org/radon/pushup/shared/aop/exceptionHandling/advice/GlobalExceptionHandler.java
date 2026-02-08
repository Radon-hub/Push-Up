package org.radon.pushup.shared.aop.exceptionHandling.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.radon.pushup.shared.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExceptionModel.class)
    public ResponseEntity<ErrorResponse> handleException(ExceptionModel model,HttpServletRequest request) {
        return model.makeResponse(request.getRequestURI());
    }

}

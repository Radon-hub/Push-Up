package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiKeyNotFoundException extends ExceptionModel {
    public ApiKeyNotFoundException() {
        super("Api key not found!", HttpStatus.NOT_FOUND);
    }
}

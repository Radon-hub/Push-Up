package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AppNotFoundException extends ExceptionModel {
    public AppNotFoundException() {
        super("App not found!", HttpStatus.NOT_FOUND);
    }
}

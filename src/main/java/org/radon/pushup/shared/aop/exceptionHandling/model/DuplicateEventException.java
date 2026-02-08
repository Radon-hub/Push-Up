package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class DuplicateEventException extends ExceptionModel {
    public DuplicateEventException() {
        super("Event already processed!", HttpStatus.OK);
    }
}

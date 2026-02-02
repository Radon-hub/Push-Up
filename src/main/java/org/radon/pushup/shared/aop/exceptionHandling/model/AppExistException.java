package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppExistException extends ExceptionModel {
    public AppExistException() {
        super("Another app exist with this name!",HttpStatus.CONFLICT);
    }
}

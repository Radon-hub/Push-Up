package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistException extends ExceptionModel {
    public UserExistException() {
        super("User already exist!",HttpStatus.CONFLICT);
    }
}

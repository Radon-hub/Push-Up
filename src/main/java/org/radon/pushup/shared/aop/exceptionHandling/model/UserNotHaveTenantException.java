package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotHaveTenantException extends ExceptionModel {
    public UserNotHaveTenantException() {
        super("User does not have a tenant!", HttpStatus.NOT_FOUND);
    }
}

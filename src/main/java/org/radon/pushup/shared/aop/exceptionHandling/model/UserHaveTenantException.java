package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserHaveTenantException extends ExceptionModel {
    public UserHaveTenantException() {
        super("User already have a tenant!", HttpStatus.BAD_GATEWAY);
    }
}

package org.radon.pushup.shared.aop.exceptionHandling.model;

import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TenantExistException extends ExceptionModel {
    public TenantExistException() {
        super("Tenant already exist!", HttpStatus.CONFLICT);
    }
}

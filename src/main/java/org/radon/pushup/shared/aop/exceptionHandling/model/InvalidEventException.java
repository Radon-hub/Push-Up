package org.radon.pushup.shared.aop.exceptionHandling.model;

import lombok.Getter;
import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEventException extends ExceptionModel {

    private final String tenantId;
    private final String appId;

    public InvalidEventException(String message, String tenantId, String appId) {
        super(message,HttpStatus.BAD_REQUEST);
        this.tenantId = tenantId;
        this.appId = appId;
    }
}

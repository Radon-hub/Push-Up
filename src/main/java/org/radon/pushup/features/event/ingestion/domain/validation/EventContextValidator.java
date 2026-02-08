package org.radon.pushup.features.event.ingestion.domain.validation;

import org.radon.pushup.features.app.domain.ApiKeyStatus;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyException;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.AppNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidEventException;

import java.util.stream.Collectors;

public class EventContextValidator {


    public static void validate(Tenant tenant, SendEventRequest request) {

        var app = tenant.getApps().stream().findFirst().orElseThrow(AppNotFoundException::new);

        var apiKey = app.getApi_key().stream().findFirst().orElseThrow(ApiKeyNotFoundException::new);

        if(apiKey.getApi_key_status() == ApiKeyStatus.DISABLED) {
            throw new ApiKeyException("API KEY status is not ACTIVE!");
        }else if(apiKey.getApi_key_status() == ApiKeyStatus.REVOKED) {
            throw new ApiKeyException("This API KEY is no longer valid!");
        }else if(apiKey.getApi_key_status() == ApiKeyStatus.ROTATED) {
            throw new ApiKeyException("API KEY has been changed!");
        }

        if(app.getStatus() != AppStatus.ACTIVE) {
            throw new InvalidEventException("App status is not ACTIVE!",tenant.getId().toString(),app.getId().toString());
        }

        var filteredPlatform = app.getPlatform().stream().filter(item -> item == request.platform()).collect(Collectors.toSet());

        if(filteredPlatform.isEmpty()){
            throw new InvalidEventException("Can not send event on this platform!",tenant.getId().toString(),app.getId().toString());
        }

        if(tenant.getStatus() != TenantStatus.ACTIVE) {
            throw new InvalidEventException("Tenant not able to send events!",tenant.getId().toString(),app.getId().toString());
        }

    }

}

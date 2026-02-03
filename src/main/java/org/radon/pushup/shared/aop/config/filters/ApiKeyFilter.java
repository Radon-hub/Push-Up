package org.radon.pushup.shared.aop.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.radon.pushup.features.app.domain.ApiKeyStatus;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyException;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidArgsException;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/event");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var apiKey = request.getHeader("X-API-KEY");

        if(apiKey == null || apiKey.isBlank()) {
            throw new ApiKeyException("API KEY is missing!");
        }

        filterChain.doFilter(request, response);


    }

}

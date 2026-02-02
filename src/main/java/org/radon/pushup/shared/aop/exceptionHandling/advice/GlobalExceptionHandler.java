package org.radon.pushup.shared.aop.exceptionHandling.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.radon.pushup.shared.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(ApiKeyException.class)
    public ResponseEntity<ErrorResponse> handleApiKeyException(ApiKeyException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(ApiKeyExistException.class)
    public ResponseEntity<ErrorResponse> handleApiKeyExistException(ApiKeyExistException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(AppExistException.class)
    public ResponseEntity<ErrorResponse> handleAppExistException(AppExistException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAppNotFoundException(AppNotFoundException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<ErrorResponse> handleCredentialException(CredentialException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<ErrorResponse> handleInvalidApiKeyException(InvalidApiKeyException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(InvalidArgsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgsException(InvalidArgsException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(PlatformNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlatformNotFoundException(PlatformNotFoundException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(TenantAccessException.class)
    public ResponseEntity<ErrorResponse> handleTenantAccessException(TenantAccessException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(TenantExistException.class)
    public ResponseEntity<ErrorResponse> handleTenantExistException(TenantExistException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTenantNotFoundException(TenantNotFoundException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponse> handleUserExistException(UserExistException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(UserHaveTenantException.class)
    public ResponseEntity<ErrorResponse> handleUserHaveTenantException(UserHaveTenantException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }

    @ExceptionHandler(UserNotHaveTenantException.class)
    public ResponseEntity<ErrorResponse> handleUserNotHaveTenantException(UserNotHaveTenantException exception, HttpServletRequest request){
        return exception.makeResponse(request.getRequestURI());
    }


}

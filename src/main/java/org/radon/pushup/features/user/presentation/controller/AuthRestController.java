package org.radon.pushup.features.user.presentation.controller;


import org.radon.pushup.features.user.application.port.in.CreateUserUseCase;
import org.radon.pushup.features.user.application.port.in.LoginUserUseCase;
import org.radon.pushup.features.user.application.port.in.RefreshTokenUseCase;
import org.radon.pushup.features.user.application.port.in.SignUpUseCase;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;
import org.radon.pushup.features.user.presentation.dto.RefreshTokenRequest;
import org.radon.pushup.features.user.presentation.dto.UserAuthTokensResponse;
import org.radon.pushup.features.user.presentation.dto.UserCreateRequest;
import org.radon.pushup.features.user.presentation.dto.UserLoginRequest;
import org.radon.pushup.features.user.presentation.mapper.UserDtoMappers;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthRestController {

    private final LoginUserUseCase loginUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final SignUpUseCase signUpUseCase;

    public AuthRestController(LoginUserUseCase loginUserUseCase, RefreshTokenUseCase refreshTokenUseCase, SignUpUseCase signUpUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.signUpUseCase = signUpUseCase;
    }

    @PostMapping("sign-up")
    public ResponseEntity<Response<String>> singUp(@RequestBody UserCreateRequest userCreateRequest) {
        var login = signUpUseCase.signUp(UserDtoMappers.createUserFromDto(userCreateRequest));
        return ResponseEntity.ok().body(new Response<>("User created successfully",null));
    }

    @PostMapping("login")
    public ResponseEntity<UserAuthTokensResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        var login = loginUserUseCase.login(userLoginRequest);
        return ResponseEntity.ok().body(login);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<UserAuthTokensResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        var generated = refreshTokenUseCase.refreshToken(request.refreshToken());
        return ResponseEntity.ok().body(generated);
    }

}

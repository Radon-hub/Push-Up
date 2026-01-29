package org.radon.pushup.features.user.presentation.controller;


import org.radon.pushup.features.user.application.port.in.CreateUserUseCase;
import org.radon.pushup.features.user.application.port.in.LoginUserUseCase;
import org.radon.pushup.features.user.application.port.in.RefreshTokenUseCase;
import org.radon.pushup.features.user.presentation.dto.UserAuthTokensResponse;
import org.radon.pushup.features.user.presentation.dto.UserLoginRequest;
import org.radon.pushup.features.user.presentation.mapper.UserDtoMappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthRestController {

    private final LoginUserUseCase loginUserUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthRestController(LoginUserUseCase loginUserUseCase, RefreshTokenUseCase refreshTokenUseCase) {
        this.loginUserUseCase = loginUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping("login")
    public ResponseEntity<UserAuthTokensResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        try{
            var login = loginUserUseCase.login(userLoginRequest);
            return ResponseEntity.ok().body(login);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UserAuthTokensResponse(e.getMessage(),"failed to generate token!"));
        }
    }

    @PostMapping("refresh-token")
    public ResponseEntity<UserAuthTokensResponse> refreshToken(@RequestParam String refreshToken) {
        try{
            var generated = refreshTokenUseCase.refreshToken(refreshToken);
            return ResponseEntity.ok().body(generated);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UserAuthTokensResponse(e.getMessage(),"failed to generate tokens!"));
        }
    }

}

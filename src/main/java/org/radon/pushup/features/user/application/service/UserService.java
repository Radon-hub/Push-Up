package org.radon.pushup.features.user.application.service;

import org.radon.pushup.features.user.application.port.in.RefreshTokenUseCase;
import org.radon.pushup.features.user.application.port.in.SignUpUseCase;
import org.radon.pushup.shared.aop.config.JWTUtil;
import org.radon.pushup.features.user.application.port.in.CreateUserUseCase;
import org.radon.pushup.features.user.application.port.in.LoginUserUseCase;
import org.radon.pushup.features.user.application.port.out.UserRepository;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.presentation.dto.UserAuthTokensResponse;
import org.radon.pushup.features.user.presentation.dto.UserLoginRequest;
import org.radon.pushup.shared.aop.exceptionHandling.model.CredentialException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService implements CreateUserUseCase, LoginUserUseCase, SignUpUseCase, RefreshTokenUseCase {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public UserAuthTokensResponse login(UserLoginRequest userLoginRequest) {
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new CredentialException("Invalid username or password!");
        }

        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return new UserAuthTokensResponse(token, refreshToken);
    }

    @Override
    public UserAuthTokensResponse refreshToken(String refreshToken) {
        var user = jwtUtil.extractUser(refreshToken);

        if(jwtUtil.isTokenValid(refreshToken, user)){
            String token = jwtUtil.generateToken(user);
            String newRefreshToken = jwtUtil.generateRefreshToken(user);
            return new UserAuthTokensResponse(token, newRefreshToken);
        }

        throw new CredentialException("Invalid refresh token!");
    }

    @Override
    public User signUp(User user) {
        return userRepository.signUp(user);
    }
}

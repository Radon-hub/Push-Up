package org.radon.pushup.features.user.presentation.controller;

import org.radon.pushup.features.user.application.port.in.CreateUserUseCase;
import org.radon.pushup.features.user.presentation.dto.UserCreateRequest;
import org.radon.pushup.features.user.presentation.mapper.UserDtoMappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserRestController {

    private final CreateUserUseCase createUserUseCase;

    public UserRestController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        try{
            createUserUseCase.createUser(UserDtoMappers.createUserFromDto(userCreateRequest));
            return ResponseEntity.ok().body("success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}

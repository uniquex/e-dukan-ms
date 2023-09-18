package com.example.edukan.controller;

import com.example.edukan.model.dto.TokenDto;
import com.example.edukan.model.dto.UserDto;
import com.example.edukan.model.request.SignInRequest;
import com.example.edukan.model.request.UserRequest;
import com.example.edukan.entity.UserEntity;
import com.example.edukan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public UserDto getUser (@PathVariable Long id){
        return service.getUser(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers (){
        return service.getUsers();
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp (@RequestBody @Valid UserRequest request) {
        return service.signUp(request);
    }

    @PostMapping("/signIn")
    public TokenDto signIn(@RequestBody SignInRequest request){
        return service.signIn(request);
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable Long id){
        return service.deleteUser(id);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id,
                                                 @RequestBody UserRequest request) throws Exception {
        return service.updateUser(id,request);
    }

    @PatchMapping("/updatePassword/{id}")
    public UserDto updatePassword (@PathVariable Long id,
                                   @RequestHeader String password) throws Exception {
        return service.updatePassword(id,password);
    }
}
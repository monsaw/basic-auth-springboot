package com.example.demo.controller;


import com.example.demo.model.Library;
import com.example.demo.model.Users;
import com.example.demo.service.LibraryService;
import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping()
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        return ResponseEntity.ok(usersService.createUser(users));
    }
}

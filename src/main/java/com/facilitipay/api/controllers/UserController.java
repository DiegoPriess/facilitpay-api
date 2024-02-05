package com.facilitipay.api.controllers;

import com.facilitipay.api.dto.UserRequest;
import com.facilitipay.api.dto.UserResponse;
import com.facilitipay.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserController(final UserService service) {
        this.service = service;
    }

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(users));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest user) {
        return ResponseEntity.ok(service.update(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @GetMapping("/{page}/{size}")
    public Page<UserResponse> list(@PathVariable Integer page,
                                   @PathVariable Integer size) {
        return service.listPage(page, size);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<UserResponse> login(@PathVariable String email,
                                              @PathVariable String password) {
        return this.service.login(email, password).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                                                  .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}

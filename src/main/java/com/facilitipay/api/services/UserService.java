package com.facilitipay.api.services;


import com.facilitipay.api.dto.UserRequest;
import com.facilitipay.api.dto.UserResponse;
import com.facilitipay.api.models.User;
import com.facilitipay.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    private final UserRepository repository;

    public UserResponse create(final UserRequest request) {
        Assert.isTrue(this.repository.findByEmail(request.getEmail()).isEmpty(), "Já existe um usuário cadastrado com o email informado.");
        final User userCreated = this.repository.save(User.builder()
                                                          .name(request.getName())
                                                          .email(request.getEmail())
                                                          .password(request.getPassword())
                                                          .build());

        return this.buildUserResponse(userCreated);
    }

    public UserResponse update(final UserRequest request) {
        Assert.notNull(request.getId(), "Id deve ser informado");
        Assert.isTrue(this.getById(request.getId()).isPresent(), "Usuário não encontrado");
        final User userUpdated = this.repository.save(User.builder()
                                                          .id(request.getId())
                                                          .name(request.getName())
                                                          .email(request.getEmail())
                                                          .password(request.getPassword())
                                                          .build());
        return this.buildUserResponse(userUpdated);
    }

    public void delete(final Long id) {
        this.repository.deleteById(id);
    }

    public Optional<UserResponse> getById(final Long id) {
        return this.repository.findById(id).map(this::buildUserResponse);
    }

    public Page<UserResponse> listPage(final Integer page, final Integer size) {
        return this.repository.findAll(PageRequest.of(page, size)).map(this::buildUserResponse);
    }

    public Optional<UserResponse> login(final String email, final String password) {
        return this.repository.findByEmailAndPassword(email, password).map(this::buildUserResponse);
    }

    private UserResponse buildUserResponse(final User user) {
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

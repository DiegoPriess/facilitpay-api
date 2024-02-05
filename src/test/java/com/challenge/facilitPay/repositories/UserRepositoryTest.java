package com.challenge.facilitPay.repositories;

import com.challenge.facilitPay.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepositoryTest(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    private final EntityManager entityManager;

    private final UserRepository userRepository;



    @Test
    @DisplayName("Should get user successfully from database")
    void findByEmailCase1() {
        final User user = User.builder().name("Teste Unitario")
                                        .email("testeUnitario@gmail.com")
                                        .password("123")
                                        .build();

        this.createUser(user);

        Optional<User> opUser = this.userRepository.findByEmail(user.getEmail());

        assertThat(opUser.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user from database when user not exists")
    void findByEmailCase2() {
        final String email = "123@gmail.com";
        Optional<User> opUser = this.userRepository.findByEmail(email);
        assertThat(opUser.isEmpty()).isTrue();
    }

    private User createUser(User user) {
        this.entityManager.persist(user);
        return user;
    }
}
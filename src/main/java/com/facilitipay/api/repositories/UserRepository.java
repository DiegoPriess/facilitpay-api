package com.facilitipay.api.repositories;

import com.facilitipay.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    Optional<User> findByEmailAndPassword(final String email, final String password);
}

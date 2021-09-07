package spring.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

/**
 * Created by Jacaranda Perez
 * Date: 2021-09-07
 * Project: Security
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findById(String username);
}

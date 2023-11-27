package dev.ruben.funkos.config.auth.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import dev.ruben.users.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

package tn.pi.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.pi.usermanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

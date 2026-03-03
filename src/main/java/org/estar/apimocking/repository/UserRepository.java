package org.estar.apimocking.repository;

import org.estar.apimocking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);
}

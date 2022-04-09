package com.security.jwtcomp.repository;

import com.security.jwtcomp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
}

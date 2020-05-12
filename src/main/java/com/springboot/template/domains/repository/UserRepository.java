package com.springboot.template.domains.repository;


import com.springboot.template.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository("user")
public interface UserRepository extends JpaRepository<User, BigInteger> {
    User findByEmailIgnoreCase(String email);
    User findById(Integer id);
}
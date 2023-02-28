package com.bolshakovk.favthingsservice.repository;

import com.bolshakovk.favthingsservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(@Param("email")String email);

    User findByActivationCode(String code);
}

package com.llb.fllbwebsite.repositories;

import com.llb.fllbwebsite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String userEmail);
}

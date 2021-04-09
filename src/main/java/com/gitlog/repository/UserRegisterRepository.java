package com.gitlog.repository;

import com.gitlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterRepository extends JpaRepository<User, Long> {
}

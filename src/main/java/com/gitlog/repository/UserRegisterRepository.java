package com.gitlog.repository;

import com.gitlog.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterRepository extends JpaRepository<Account, Long> {
}

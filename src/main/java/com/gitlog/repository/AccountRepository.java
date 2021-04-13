package com.gitlog.repository;

import com.gitlog.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNickname(String Nickname);
    Optional<Account> findByEmail(String Email);
    Boolean existsByNickname(String Nickname);
    Boolean existsByEmail(String Email);
}

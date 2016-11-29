package com.joinme.backend.accounts.repository;


import com.joinme.backend.accounts.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);

    UserAccount findByEmail(String email);
}
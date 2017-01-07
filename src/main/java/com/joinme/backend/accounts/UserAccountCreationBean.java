package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserAccountCreationBean implements UserAccountCreation {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TODO encrypt password using salt and so on -> see Spring security example

    @Override
    public void createUser(AccountRegistrationData accountRegistrationData) {
        UserAccount userAccount = convertRegistrationDataToAccount(accountRegistrationData);
        userAccountRepository.save(userAccount);
    }

    private UserAccount convertRegistrationDataToAccount(AccountRegistrationData accountRegistrationData) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(accountRegistrationData.getEmail());
        userAccount.setUsername(accountRegistrationData.getUsername());

        String rawPassword = accountRegistrationData.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userAccount.setPassword(encodedPassword);

        return userAccount;
    }
}

package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.AccountRegistrationData;

public interface UserAccountCreation {
    void createUser(AccountRegistrationData accountRegistrationData);
}

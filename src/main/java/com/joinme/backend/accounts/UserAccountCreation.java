package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.AccountRegistrationData;
/**
 * Created by Nicole, August 2017.
 */
public interface UserAccountCreation {
    void createUser(AccountRegistrationData accountRegistrationData);
}

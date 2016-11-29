package com.joinme.frontend.api.controller.accounts;

import com.joinme.backend.accounts.UserAccountCreation;
import com.joinme.backend.accounts.dto.AccountRegistrationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class AccountCreationController {

    @Autowired
    private UserAccountCreation userAccountCreation;

    @RequestMapping(value = "/api/accounts", method = RequestMethod.POST)
    @ResponseBody
    public void createAccount(@Valid @RequestBody AccountRegistrationData accountRegistrationData) {
        userAccountCreation.createUser(accountRegistrationData);
    }
}

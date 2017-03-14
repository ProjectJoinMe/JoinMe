package com.joinme.frontend.api.controller.accounts;

import com.joinme.backend.accounts.UserAccountCreation;
import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AccountCreationController {

    @Autowired
    private UserAccountCreation userAccountCreation;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @RequestMapping(value = "/api/accounts/register", method = RequestMethod.POST)
    @ResponseBody
    public void createAccount(@Valid @RequestBody AccountRegistrationData accountRegistrationData) {
        userAccountCreation.createUser(accountRegistrationData);
    }

    @RequestMapping(value = "/api/accounts/exists/byUsername/{username}", method = RequestMethod.GET)
    @ResponseBody
    public boolean isAccountWithUsernameExisting(@PathVariable("username") String username) {
        UserAccount account = userAccountRepository.findByUsername(username);
        String bla = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userAccountRepository.findByUsername(bla);
        return account != null;
    }

    @RequestMapping(value = "/api/accounts/exists/byEmail/{email}", method = RequestMethod.GET)
    @ResponseBody
    public boolean isAccountWithEmailExisting(@PathVariable("email") String email) {
        UserAccount account = userAccountRepository.findByEmail(email);
        return account != null;
    }
}

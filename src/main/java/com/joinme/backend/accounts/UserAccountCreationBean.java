package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.AccountRegistrationData;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
@Transactional
public class UserAccountCreationBean implements UserAccountCreation {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(AccountRegistrationData accountRegistrationData) {
        UserAccount userAccount = convertRegistrationDataToAccount(accountRegistrationData);
        userAccountRepository.save(userAccount);
    }

    private UserAccount convertRegistrationDataToAccount(AccountRegistrationData accountRegistrationData) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(accountRegistrationData.getEmail());
        userAccount.setUsername(accountRegistrationData.getUsername());
        userAccount.setGender(accountRegistrationData.getGender());
        userAccount.setDateOfBirth(LocalDate.from(LocalDateTime.ofInstant(accountRegistrationData.getDateOfBirth().toInstant(), ZoneId.systemDefault())));
        userAccount.setFirstName(accountRegistrationData.getFirstName());
        userAccount.setLastName(accountRegistrationData.getLastName());

        String rawPassword = accountRegistrationData.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userAccount.setPassword(encodedPassword);

        return userAccount;
    }

    private Date truncateTime(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }
}

package com.joinme.backend.accounts;

import com.joinme.backend.accounts.coverter.UserAccountConverter;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Alexander on 17.08.2017.
 */
@Component
@Transactional
public class UserProfileManagerBean implements UserProfileManager {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountConverter userAccountConverter;

    @Override
    public UserProfileDto getProfile(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        return userAccountConverter.toDto(userAccount);
    }

    @Override
    public UserProfileDto updateUserProfile(UserProfileDto userProfile) {
        UserAccount userAccount = userAccountRepository.findByUsername(userProfile.getUsername());
        userAccountConverter.setPropertiesOnEntity(userAccount, userProfile);
        return userAccountConverter.toDto(userAccount);
    }
}

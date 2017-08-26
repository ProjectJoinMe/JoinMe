package com.joinme.backend.accounts;

import com.joinme.backend.accounts.coverter.UserAccountConverter;
import com.joinme.backend.accounts.dto.UserPasswordDto;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public UserProfileDto updateUserPassword(UserPasswordDto userPasswordDto) {
        String rawPassword = userPasswordDto.getPassword();
        System.out.println(rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword);

        UserAccount userAccount = userAccountRepository.findByUsername(userPasswordDto.getUsername());
        userAccount.setPassword(encodedPassword);

        return userAccountConverter.toDto(userAccount);
    }

    @Override
    public UserProfileDto setProfilePicture(String username, MultipartFile profilePicture) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        try {
            userAccount.setProfilePicture(profilePicture.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAccountConverter.toDto(userAccount);

    }
}

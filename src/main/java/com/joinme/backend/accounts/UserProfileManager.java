package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.UserPasswordDto;
import com.joinme.backend.accounts.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Alexander on 17.08.2017.
 */
public interface UserProfileManager {

    UserProfileDto getProfile(String username);

    UserProfileDto updateUserProfile(UserProfileDto userProfile);

    UserProfileDto updateUserPassword(UserPasswordDto userPasswordDto);

    UserProfileDto setProfilePicture(String username, MultipartFile profilePicture);
}

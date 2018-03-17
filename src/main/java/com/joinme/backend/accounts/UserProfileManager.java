package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.PointOfInterestDto;
import com.joinme.backend.accounts.dto.UserPasswordDto;
import com.joinme.backend.accounts.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Alexander on 17.08.2017.
 */
public interface UserProfileManager {

    UserProfileDto getProfile(String username);

    UserProfileDto updateUserProfile(UserProfileDto userProfile);

    void updatePointsOfInterest(String currentUsername, List<PointOfInterestDto> pointsOfInterest);

    List<PointOfInterestDto> getPointsOfInterest(String username);

    UserProfileDto updateUserPassword(UserPasswordDto userPasswordDto);

    UserProfileDto setProfilePicture(String username, MultipartFile profilePicture);

    UserProfileDto setCarPicture(String username, MultipartFile carPicture);

    byte[] getProfilePicture(String username);

    byte[] getCarPicture(String username);
}

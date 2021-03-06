package com.joinme.backend.accounts;

import com.joinme.backend.accounts.converter.UserAccountConverter;
import com.joinme.backend.accounts.dto.PointOfInterestDto;
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
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Returns the UserProfileDto with the username
     * @param username
     * @return
     */
    @Override
    public UserProfileDto getProfile(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        return userAccountConverter.toDto(userAccount);
    }

    /**
     * Updates properties of UserProfile
     * @param userProfile
     * @return
     */
    @Override
    public UserProfileDto updateUserProfile(UserProfileDto userProfile) {
        UserAccount userAccount = userAccountRepository.findByUsername(userProfile.getUsername());
        userAccountConverter.setPropertiesOnEntity(userAccount, userProfile);
        return userAccountConverter.toDto(userAccount);
    }

    /**
     * Updates PointsOfInterest
     * @param username
     * @param pointsOfInterest
     */
    @Override
    public void updatePointsOfInterest(String username, List<PointOfInterestDto> pointsOfInterest) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        userAccount.setPointsOfInterest(new ArrayList<>(pointsOfInterest));
    }

    /**
     * Returns PointsOfInterset of User with username
     * @param username
     * @return
     */
    @Override
    public List<PointOfInterestDto> getPointsOfInterest(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        ArrayList<PointOfInterestDto> pointsOfInterest = userAccount.getPointsOfInterest();
        if (pointsOfInterest == null) {
            pointsOfInterest = new ArrayList<>();
        }
        return pointsOfInterest;
    }

    /**
     * Updates user password
     * @param userPasswordDto new password
     * @return
     */
    @Override
    public UserProfileDto updateUserPassword(UserPasswordDto userPasswordDto) {
        String rawPassword = userPasswordDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserAccount userAccount = userAccountRepository.findByUsername(userPasswordDto.getUsername());
        userAccount.setPassword(encodedPassword);

        return userAccountConverter.toDto(userAccount);
    }

    /**
     * Sets Profile Picuture for user
     * @param username
     * @param profilePicture
     * @return
     */
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

    /**
     * Sets profile car picture for user
     * @param username
     * @param carPicture
     * @return
     */
    @Override
    public UserProfileDto setCarPicture(String username, MultipartFile carPicture) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        try {
            userAccount.setCarPicture(carPicture.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userAccountConverter.toDto(userAccount);

    }

    /**
     * Returns profile picture of user
     * @param username
     * @return
     */
    @Override
    public byte[] getProfilePicture(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        byte[] profilePicture = userAccount.getProfilePicture();

        if (profilePicture != null && profilePicture.length != 0) {
            return profilePicture;
        } else {
            return new byte[1];
        }
    }

    /**
     * Returns car picture of user
     * @param username
     * @return
     */
    @Override
    public byte[] getCarPicture(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        byte[] carPicture = userAccount.getCarPicture();

        if (carPicture != null && carPicture.length != 0) {
            return carPicture;
        } else {
            return new byte[1];
        }
    }
}

package com.joinme.frontend.api.controller.accounts;

import com.joinme.backend.accounts.UserProfileManager;
import com.joinme.backend.accounts.dto.PointOfInterestDto;
import com.joinme.backend.accounts.dto.UserPasswordDto;
import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.frontend.api.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Alexander on 17.08.2017.
 */
@Controller
public class UserProfileController {

    @Autowired
    private UserProfileManager userProfileManager;

    /**
     * Returns the UserProfileDto matching the given username in the HTTP get-request received.
     * The UserProfileDto is gotten from the UserProfileManager
     *
     * @param username
     * @return The UserProfileDto matching the given username
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}", method = RequestMethod.GET)
    @ResponseBody
    public UserProfileDto getUserProfileByUsername(@PathVariable String username) {
        UserProfileDto userProfile = userProfileManager.getProfile(username);
        return userProfile;
    }

    /**
     * Updates the UserProfile in question with the data received in the HTTP put-request and
     * returns the updated UserProfileDto. Data is managed in the UserProfileManager
     *
     * @param username    of the user that wants to update
     * @param userProfile old UserProfileDto of the user
     * @return UserProfileDto of the updated UserProfile
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/update", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto updateUserProfile(@PathVariable String username, @Valid @RequestBody UserProfileDto userProfile) {
        Assert.isTrue(userProfile.getUsername().equals(username));
        Assert.isTrue(userProfile.getUsername().equals(SecurityUtil.getCurrentUsername())); //checks for valid user
        return userProfileManager.updateUserProfile(userProfile);
    }

    /**
     * Returns the points of interest of the currently logged in user. Data is requested from the UserProfileManager
     *
     * @return List of PointOfInterestDto containing the users PointsOfInterest
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/getPointsOfInterest", method = RequestMethod.GET)
    @ResponseBody
    public List<PointOfInterestDto> getPointsOfInterest() {
        List<PointOfInterestDto> pois = userProfileManager.getPointsOfInterest(SecurityUtil.getCurrentUsername());
        return pois;
    }

    /**
     * Updates the PointsOfInterest of the currently logged in user. Forwarding data to the UserProfileManager
     *
     * @param poiDto List of PointOfInterestDto for the user
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/updatePointsOfInterest", method = RequestMethod.PUT)
    @ResponseBody
    public void updatePointsOfInterest(@Valid @RequestBody List<PointOfInterestDto> poiDto) {
        userProfileManager.updatePointsOfInterest(SecurityUtil.getCurrentUsername(), poiDto);
    }

    /**
     * Receives request to update the password of the user with the new one provided in the UserPasswordDto.
     * Actual update handling in the UserProfileManager
     *
     * @param username        of the user that updates the password
     * @param userPasswordDto contains the new password information
     * @return UserProfileDto of the user that updates the password
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/updatePassword", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto updateUserPassword(@PathVariable String username, @RequestBody UserPasswordDto userPasswordDto) {
        Assert.isTrue(username.equals(userPasswordDto.getUsername()));
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername())); //checks for valid user
        return userProfileManager.updateUserPassword(userPasswordDto);
    }

    /**
     * Receives the upload of the new ProfilePicture of the user. Forwards data to the UserProfileManager.
     *
     * @param username       of the user that updates the image
     * @param profilePicture The new picture received in the HTTP request.
     * @return UserProfileDto of the user that updates the picture
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/uploadProfilePicture", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto uploadUserProfilePicture(@PathVariable String username, @RequestParam("profilePicture") MultipartFile profilePicture) {
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));  //checks for valid user
        Assert.isTrue(isImage(profilePicture));  //checks that file is image
        return userProfileManager.setProfilePicture(username, profilePicture);
    }

    /**
     * Receives the upload of the new CarPicture of the user. Forwards data to the UserProfileManager.
     *
     * @param username   of the user that updates the image
     * @param carPicture The new picture received in the HTTP request.
     * @return UserProfileDto of the user that updates the picture
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/uploadCarPicture", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto uploadUserCarPicture(@PathVariable String username, @RequestParam("carPicture") MultipartFile carPicture) {
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));  //checks for valid user
        Assert.isTrue(isImage(carPicture));
        return userProfileManager.setCarPicture(username, carPicture);
    }

    /**
     * Checks whether or not the file uploaded is an image.
     *
     * @param multipartFile The file in question
     * @return boolean, true if file is image
     */
    private boolean isImage(MultipartFile multipartFile) {
        return multipartFile.getContentType().split("/")[0].equals("image");
    }

    /**
     * Configuration for the file receiving
     *
     * @return plain MultipartConfigElement needed
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    /**
     * Creates and returns the MultipartResolver needed for the upload.
     * Sets max file size to 10 MB and max inMemorySize to 2 MB.
     *
     * @return the MultipartResolver created
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760);
        multipartResolver.setMaxInMemorySize(2097152);
        return multipartResolver;
    }

    /**
     * Returns the ProfilePicture of the user with the username provided in the request.
     * Handling in the UserProfileManager.
     *
     * @param username of the UserProfile that contains the picture.
     * @return the profile picture in question
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/profilePicture", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getUserProfilePicture(@PathVariable String username) {
        return userProfileManager.getProfilePicture(username);
    }

    /**
     * Returns the CarPicture of the user with the username provided in the request.
     * Handling in the UserProfileManager.
     *
     * @param username of the UserProfile that contains the picture.
     * @return the profile picture in question
     */
    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/carPicture", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getUserCarPicture(@PathVariable String username) {
        return userProfileManager.getCarPicture(username);
    }
}
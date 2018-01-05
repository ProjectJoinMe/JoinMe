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

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}", method = RequestMethod.GET)
    @ResponseBody
    public UserProfileDto getUserProfileByUsername(@PathVariable String username) {
        UserProfileDto userProfile = userProfileManager.getProfile(username);
        return userProfile;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/update", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto updateUserProfile(@PathVariable String username, @Valid @RequestBody UserProfileDto userProfile) {
        Assert.isTrue(userProfile.getUsername().equals(username));
        Assert.isTrue(userProfile.getUsername().equals(SecurityUtil.getCurrentUsername()));
        return userProfileManager.updateUserProfile(userProfile);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/getPointsOfInterest", method = RequestMethod.GET)
    @ResponseBody
    public List<PointOfInterestDto> getPointsOfInterest() {
        List<PointOfInterestDto> pois = userProfileManager.getPointsOfInterest(SecurityUtil.getCurrentUsername());
        return pois;
    }


    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/updatePointsOfInterest", method = RequestMethod.PUT)
    @ResponseBody
    public void updatePointsOfInterest(@Valid @RequestBody List<PointOfInterestDto> poiDto) {
        userProfileManager.updatePointsOfInterest(SecurityUtil.getCurrentUsername(), poiDto);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/updatePassword", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto updateUserPassword(@PathVariable String username, @RequestBody UserPasswordDto userPasswordDto) {
        Assert.isTrue(username.equals(userPasswordDto.getUsername()));
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));
        return userProfileManager.updateUserPassword(userPasswordDto);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/uploadProfilePicture", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto uploadUserProfilePicture(@PathVariable String username, @RequestParam("profilePicture") MultipartFile profilePicture) {
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));
        System.out.println(profilePicture.getOriginalFilename());
        //TODO check if file really is an image
        return userProfileManager.setProfilePicture(username, profilePicture);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/uploadCarPicture", method = RequestMethod.PUT)
    @ResponseBody
    public UserProfileDto uploadUserCarPicture(@PathVariable String username, @RequestParam("carPicture") MultipartFile carPicture) {
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));
        //TODO check if file really is an image
        return userProfileManager.setCarPicture(username, carPicture);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(10485760);
        multipartResolver.setMaxInMemorySize(2097152);
        return multipartResolver;
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/profilePicture", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getUserProfilePicture(@PathVariable String username) {
        return userProfileManager.getProfilePicture(username);
    }

    @PreAuthorize("fullyAuthenticated")
    @RequestMapping(value = "/api/profile/{username}/carPicture", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getUserCarPicture(@PathVariable String username) {
        return userProfileManager.getCarPicture(username);
    }
}


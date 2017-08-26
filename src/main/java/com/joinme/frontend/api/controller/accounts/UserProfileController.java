package com.joinme.frontend.api.controller.accounts;

import com.joinme.backend.accounts.UserProfileManager;
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
    public UserProfileDto uploadUserProfilePicture(@PathVariable String username, @RequestParam("pictureFile") MultipartFile profilePicture) {
        Assert.isTrue(username.equals(SecurityUtil.getCurrentUsername()));
        return userProfileManager.setProfilePicture(username, profilePicture);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5242880);
        multipartResolver.setMaxInMemorySize(1048576);
        return multipartResolver;
    }
}


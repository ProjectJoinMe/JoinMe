package com.joinme.frontend.api.controller.accounts;

import com.joinme.backend.accounts.UserProfileManager;
import com.joinme.backend.accounts.dto.UserProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}

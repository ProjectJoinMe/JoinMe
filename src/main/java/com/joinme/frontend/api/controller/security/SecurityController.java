package com.joinme.frontend.api.controller.security;

import com.joinme.frontend.api.data.security.SecurityStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Nicole on 01.05.2017.
 */
@Controller
public class SecurityController {

    @RequestMapping(value = "/api/security/status", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("permitAll()")
    public SecurityStatus getSecurityStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            // anonymous
        } else {
            username = authentication.getPrincipal().toString();
        }

        SecurityStatus securityStatus = new SecurityStatus();
        securityStatus.setUsername(username);
        return securityStatus;
    }
}

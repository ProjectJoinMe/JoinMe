package com.joinme.backend.accounts;

import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alexander on 17.08.2017.
 */
public interface UserProfileManager {

    UserProfileDto getProfile(String username);

    UserProfileDto updateUserProfile(UserProfileDto userProfile);
}

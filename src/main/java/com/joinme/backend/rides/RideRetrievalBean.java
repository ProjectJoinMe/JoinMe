package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class RideRetrievalBean implements RideRetrieval {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public List<Ride> getRidesOf(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        return rideRepository.findByProviderOrderByCreationDateTimeDesc(userAccount);
    }
}
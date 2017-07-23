package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
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

    @Autowired
    private RideConverter rideConverter;

    @Override
    public List<RideDto> getRidesOf(String username) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        List<Ride> rides = rideRepository.findByProviderOrderByCreationDateTimeDesc(userAccount);
        return rideConverter.toDto(rides);
    }
}
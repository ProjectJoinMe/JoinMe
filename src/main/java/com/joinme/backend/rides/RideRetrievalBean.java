package com.joinme.backend.rides;

import com.joinme.backend.accounts.entity.UserAccount;
import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.converter.RideConverter;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideSearchFilter;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public RideDto getRideById(long id) {
        Ride ride = rideRepository.findById(id);
        return rideConverter.toDto(ride);
    }

    @Override
    public List<RideDto> searchRides(RideSearchFilter rideSearchFilter) {
        if (rideSearchFilter.getStart() == null
                && rideSearchFilter.getDestination() == null
                && rideSearchFilter.getDate() == null) {
            throw new IllegalArgumentException("at least one argument for the search has to be provided");
        }

        Date date = rideSearchFilter.getDate() != null ? Date.from(rideSearchFilter.getDate().atStartOfDay().toInstant(ZoneOffset.UTC)) : null;
        return rideRepository.search(rideSearchFilter.getStart(), rideSearchFilter.getDestination(), date).stream()
                .map(rideConverter::toDto)
                .collect(Collectors.toList());
    }
}
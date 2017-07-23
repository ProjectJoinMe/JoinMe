package com.joinme.backend.rides.converter;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.entity.Ride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nicole on 23.07.2017.
 */
@Component
@Transactional
public class RideConverter {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<RideDto> toDto(List<Ride> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RideDto toDto(Ride entity) {
        RideDto rideDto = new RideDto();
        rideDto.setId(entity.getId());
        rideDto.setStart(entity.getStart());
        rideDto.setDestination(entity.getDestination());
        rideDto.setDepartureDateTime(entity.getDepartureDateTime());
        rideDto.setMaxPassengers(entity.getMaxPassengers());
        rideDto.setReturnDepartureDateTime(entity.getReturnDepartureDateTime());
        rideDto.setNotes(entity.getNotes());
        rideDto.setCreationDateTime(entity.getCreationDateTime());
        rideDto.setProviderUsername(entity.getProvider().getUsername());
        return rideDto;
    }

    public Ride toEntity(RideDto rideDto) {
        Ride rideEntity = new Ride();
        rideEntity.setId(rideDto.getId());
        rideEntity.setStart(rideDto.getStart());
        rideEntity.setDestination(rideDto.getDestination());
        rideEntity.setDepartureDateTime(rideDto.getDepartureDateTime());
        rideEntity.setMaxPassengers(rideDto.getMaxPassengers());
        rideEntity.setReturnDepartureDateTime(rideDto.getReturnDepartureDateTime());
        rideEntity.setNotes(rideDto.getNotes());
        rideEntity.setCreationDateTime(rideDto.getCreationDateTime());
        rideEntity.setProvider(userAccountRepository.findByUsername(rideDto.getProviderUsername()));
        return rideEntity;
    }
}

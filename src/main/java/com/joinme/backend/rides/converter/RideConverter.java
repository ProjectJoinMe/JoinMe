package com.joinme.backend.rides.converter;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.location.BorderBox;
import com.joinme.backend.location.LatLng;
import com.joinme.backend.rides.dto.RideDto;
import com.joinme.backend.rides.dto.RideRouteDto;
import com.joinme.backend.rides.entity.Ride;
import com.joinme.backend.rides.repository.RideJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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

    @Autowired
    private RideJoinRepository rideJoinRepository;

    public List<RideDto> toDto(List<Ride> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RideDto toDto(Ride entity) {
        RideDto rideDto = new RideDto();
        setPropertiesOnDto(rideDto, entity);
        return rideDto;
    }

    public void setPropertiesOnDto(RideDto rideDto, Ride entity) {
        rideDto.setId(entity.getId());
        rideDto.setStart(entity.getStart());
        rideDto.setStartPlaceId(entity.getStartPlaceId());
        rideDto.setDestination(entity.getDestination());
        rideDto.setDestinationPlaceId(entity.getDestinationPlaceId());
        rideDto.setDepartureDateTime(entity.getDepartureDateTime());
        rideDto.setMaxPassengers(entity.getMaxPassengers());
        rideDto.setReturnDepartureDateTime(entity.getReturnDepartureDateTime());
        rideDto.setNotes(entity.getNotes());
        rideDto.setCreationDateTime(entity.getCreationDateTime());
        rideDto.setProviderUsername(entity.getProvider().getUsername());
        rideDto.setFreeSeats(entity.getMaxPassengers() - rideJoinRepository.countByRide(entity));
        rideDto.setPricePerPassenger(entity.getPricePerPassenger());
        rideDto.setPeriodicWeekDays(getPeriodicWeekDays(entity));
        RideRouteDto rideRouteDto = new RideRouteDto();
        rideRouteDto.setEncodedPathLocations(entity.getEncodedPathLocations());
        rideRouteDto.setBorderBox(new BorderBox(
                new LatLng(entity.getBorderBoxSouthWestLat(), entity.getBorderBoxSouthWestLng()),
                new LatLng(entity.getBorderBoxNorthEastLat(), entity.getBorderBoxNorthEastLng())
        ));
        rideDto.setRoute(rideRouteDto);
    }

    private List<Integer> getPeriodicWeekDays(Ride entity) {
        if (entity.getPeriodicWeekDays() == null) {
            return Collections.emptyList();
        }
        return entity.getPeriodicWeekDays().chars()
                .filter(value -> value != ',')
                .map(operand -> operand - '0')
                .boxed()
                .collect(Collectors.toList());
    }

    public Ride toEntity(RideDto rideDto) {
        Ride rideEntity = new Ride();
        setPropertiesOnEntity(rideEntity, rideDto);
        return rideEntity;
    }

    public void setPropertiesOnEntity(Ride rideEntity, RideDto rideDto) {
        rideEntity.setId(rideDto.getId());
        rideEntity.setStart(rideDto.getStart());
        rideEntity.setStartPlaceId(rideDto.getStartPlaceId());
        rideEntity.setDestination(rideDto.getDestination());
        rideEntity.setDestinationPlaceId(rideDto.getDestinationPlaceId());
        rideEntity.setDepartureDateTime(rideDto.getDepartureDateTime());
        rideEntity.setDepartureDate(rideDto.getDepartureDateTime().toLocalDate());
        rideEntity.setMaxPassengers(rideDto.getMaxPassengers());
        rideEntity.setReturnDepartureDateTime(rideDto.getReturnDepartureDateTime());
        rideEntity.setReturnDepartureDate(rideDto.getReturnDepartureDateTime() != null ? rideDto.getReturnDepartureDateTime().toLocalDate() : null);
        rideEntity.setNotes(rideDto.getNotes());
        rideEntity.setCreationDateTime(rideDto.getCreationDateTime());
        rideEntity.setPricePerPassenger(rideDto.getPricePerPassenger());
        rideEntity.setProvider(userAccountRepository.findByUsername(rideDto.getProviderUsername()));
        rideEntity.setEncodedPathLocations(rideDto.getRoute().getEncodedPathLocations());
        rideEntity.setBorderBoxSouthWestLat(rideDto.getRoute().getBorderBox().getSouthWest().lat);
        rideEntity.setPeriodicWeekDays(rideDto.getPeriodicWeekDays() == null || rideDto.getPeriodicWeekDays().isEmpty() ? null : serializePeriodicWeekDays(rideDto.getPeriodicWeekDays()));
        rideEntity.setBorderBoxSouthWestLng(rideDto.getRoute().getBorderBox().getSouthWest().lng);
        rideEntity.setBorderBoxNorthEastLat(rideDto.getRoute().getBorderBox().getNorthEast().lat);
        rideEntity.setBorderBoxNorthEastLng(rideDto.getRoute().getBorderBox().getNorthEast().lng);
    }

    private String serializePeriodicWeekDays(List<Integer> periodicWeekDays) {
        if (periodicWeekDays == null || periodicWeekDays.isEmpty()) {
            return null;
        }
        return periodicWeekDays.stream()
                .map(integer -> Integer.toString(integer))
                .collect(Collectors.joining(","));
    }
}

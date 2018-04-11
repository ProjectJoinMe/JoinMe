package com.joinme.backend.notifications.converter;

import com.joinme.backend.accounts.repository.UserAccountRepository;
import com.joinme.backend.notifications.dto.RideReferenceUserNotificationData;
import com.joinme.backend.notifications.dto.UserNotificationDto;
import com.joinme.backend.notifications.entity.UserNotification;
import com.joinme.backend.rides.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Created by Nicole, January 2018.
 */
@Component
public class UserNotificationConverter {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private RideRepository rideRepository;

    /**
     * Converts UserNotification entities to DTOs
     * @param entities
     * @return
     */
    public List<UserNotificationDto> toDto(List<UserNotification> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts UserNotification entity to DTO
     * @param entity
     * @return
     */
    public UserNotificationDto toDto(UserNotification entity) {
        UserNotificationDto dto = new UserNotificationDto();
        setPropertiesOnDto(dto, entity);
        return dto;
    }

    /**
     * Set properties of entity on DTO
     * @param dto
     * @param entity
     */
    public void setPropertiesOnDto(UserNotificationDto dto, UserNotification entity) {
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.isNotificationRead());
        dto.setType(entity.getType());
        dto.setUsername(entity.getUser().getUsername());
        dto.setCreationDateTime(entity.getCreationDateTime());
        switch (entity.getType()) {
            case rideWasJoined:
            case rideWasUnjoined:
            case rideWasUpdated:
                RideReferenceUserNotificationData typeSpecificData = new RideReferenceUserNotificationData();
                typeSpecificData.setRideId(entity.getRide().getId());
                dto.setTypeSpecificData(typeSpecificData);
                break;
            case joinedRideWasDeleted:
            case gotRating:
            case chatMessageReceived:
                break;
            default:
                throw new IllegalArgumentException("Error in UserNotificationConverter.setPropertiesOnDto");
        }
    }

    /**
     * Converts UserNotification DTO to entity
     * @param dto
     * @return
     */
    public UserNotification toEntity(UserNotificationDto dto) {
        UserNotification entity = new UserNotification();
        setPropertiesOnEntity(entity, dto);
        return entity;
    }

    /**
     * Sets properties of DTO on entity
     * @param entity
     * @param dto
     */
    public void setPropertiesOnEntity(UserNotification entity, UserNotificationDto dto) {
        entity.setId(dto.getId());
        entity.setMessage(dto.getMessage());
        entity.setNotificationRead(dto.isRead());
        entity.setType(dto.getType());
        entity.setUser(userAccountRepository.findByUsername(dto.getUsername()));
        entity.setCreationDateTime(dto.getCreationDateTime());

        switch (dto.getType()) {
            case rideWasJoined:
            case rideWasUnjoined:
            case rideWasUpdated:
                RideReferenceUserNotificationData typeSpecificData = (RideReferenceUserNotificationData) dto.getTypeSpecificData();
                entity.setRide(rideRepository.findById(typeSpecificData.getRideId()));
                break;
            case joinedRideWasDeleted:
            case gotRating:
            case chatMessageReceived:
                break;
            default:
                throw new IllegalArgumentException("Error in UserNotificationConverter.setPropertiesOnEntity");
        }
    }
}

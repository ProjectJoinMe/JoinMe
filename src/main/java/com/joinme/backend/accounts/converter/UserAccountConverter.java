package com.joinme.backend.accounts.converter;

import com.joinme.backend.accounts.dto.UserProfileDto;
import com.joinme.backend.accounts.entity.UserAccount;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 17.08.2017.
 */
@Component
@Transactional
public class UserAccountConverter {

    public List<UserProfileDto> toDto(List<UserAccount> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserProfileDto toDto(UserAccount entity) {
        UserProfileDto userProfileDto = new UserProfileDto();
        setPropertiesOnDto(userProfileDto, entity);
        return userProfileDto;
    }

    public void setPropertiesOnDto(UserProfileDto userProfileDto, UserAccount entity) {
        if (entity != null) {
            userProfileDto.setUsername(entity.getUsername());
            userProfileDto.setEmail(entity.getEmail());
            userProfileDto.setDateOfBirth(entity.getDateOfBirth());
            userProfileDto.setGender(entity.getGender());
            userProfileDto.setFirstName(entity.getFirstName());
            userProfileDto.setLastName(entity.getLastName());
            userProfileDto.setDescription(entity.getDescription());
            userProfileDto.setCarMake(entity.getCarMake());
            userProfileDto.setCarModel(entity.getCarModel());
            userProfileDto.setCarManufacturingYear(entity.getCarManufacturingYear());
            userProfileDto.setCarDescription(entity.getCarDescription());
        }
    }

    public UserAccount toEntity(UserProfileDto userProfileDto) {
        UserAccount userAccountEntity = new UserAccount();
        setPropertiesOnEntity(userAccountEntity, userProfileDto);
        return userAccountEntity;
    }

    public void setPropertiesOnEntity(UserAccount userAccountEntity, UserProfileDto userProfileDto) {
        userAccountEntity.setUsername(userProfileDto.getUsername());
        userAccountEntity.setEmail(userProfileDto.getEmail());
        userAccountEntity.setDateOfBirth(userProfileDto.getDateOfBirth());
        userAccountEntity.setGender(userProfileDto.getGender());
        userAccountEntity.setFirstName(userProfileDto.getFirstName());
        userAccountEntity.setLastName(userProfileDto.getLastName());
        userAccountEntity.setDescription(userProfileDto.getDescription());
        userAccountEntity.setCarMake(userProfileDto.getCarMake());
        userAccountEntity.setCarModel(userProfileDto.getCarModel());
        userAccountEntity.setCarManufacturingYear(userProfileDto.getCarManufacturingYear());
        userAccountEntity.setCarDescription(userProfileDto.getCarDescription());
    }
}

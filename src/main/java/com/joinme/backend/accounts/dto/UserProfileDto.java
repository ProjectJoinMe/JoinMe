package com.joinme.backend.accounts.dto;

import java.time.LocalDate;

/**
 * Created by Alexander on 17.08.2017.
 */
public class UserProfileDto {
    private String username;

    private String email;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String firstName;

    private String lastName;

    private String description;

    //Car

    private String carMake;

    private String carModel;

    private int carManufacturingYear;

    private String carDescription;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarManufacturingYear() {
        return carManufacturingYear;
    }

    public void setCarManufacturingYear(int carManufacturingYear) {
        this.carManufacturingYear = carManufacturingYear;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
}

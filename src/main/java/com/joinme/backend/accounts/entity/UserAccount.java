package com.joinme.backend.accounts.entity;

import com.joinme.backend.accounts.dto.Gender;
import com.joinme.backend.accounts.dto.PointOfInterestDto;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "UserAccount")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private String username;
    @Column(unique = true, length = 4000)
    @Email
    private String email;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    /**
     * encrypted password
     */
    @Column(nullable = false, length = 1024)
    private String password;

    @Type(type = "serializable")
    @Column(length = 6000)
    // using ArrayList instead of List to exploit Hibernate behavior: https://stackoverflow.com/questions/6622710/store-list-in-hibernate-as-serializable-object
    private ArrayList<PointOfInterestDto> pointsOfInterest;

    @Column
    private String description;

    @Lob
    @Column
    private byte[] profilePicture;

    //Car data
    @Column
    private String carMake;

    @Column
    private String carModel;

    @Column
    private Integer carManufacturingYear;

    @Column
    private String carDescription;

    @Lob
    @Column
    private byte[] carPicture;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<PointOfInterestDto> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(ArrayList<PointOfInterestDto> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    //Car getters and setters

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
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

    public Integer getCarManufacturingYear() {
        return carManufacturingYear;
    }

    public void setCarManufacturingYear(Integer carManufacturingYear) {
        this.carManufacturingYear = carManufacturingYear;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public byte[] getCarPicture() {
        return carPicture;
    }

    public void setCarPicture(byte[] carPicture) {
        this.carPicture = carPicture;
    }
}

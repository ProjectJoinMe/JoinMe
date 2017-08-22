package com.joinme.backend.accounts.entity;

import com.joinme.backend.accounts.dto.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "UserAccount")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column
    String lastName;
    @Id
    @Column
    private String username;
    @Column(unique = true, length = 4000)
    private String email;
    @Column
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;
    @Column
    private String firstName;
    /**
     * encrypted password
     */
    @Column(length = 1024)
    private String password;

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
}

package com.joinme.backend.accounts.entity;

import com.joinme.backend.accounts.dto.Gender;
import com.joinme.backend.rides.entity.Ride;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "UserAccount")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @OneToMany(mappedBy = "provider")
    private Set<Ride> simpleRides;

    /**
     * encrypted password
     */
    @Column(length = 1024)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Ride> getSimpleRides() {
        return simpleRides;
    }

    public void setSimpleRides(Set<Ride> simpleRides) {
        this.simpleRides = simpleRides;
    }
}

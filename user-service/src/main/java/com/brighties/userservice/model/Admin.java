package com.brighties.userservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;


@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
}

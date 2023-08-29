package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class WasteTruck
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String brand;
    int capacity;
    boolean isAvailable;
    String registrationNumber;

    public WasteTruck(String brand, int capacity, String registrationNumber)
    {
        this.brand = brand;
        this.capacity = capacity;
        this.registrationNumber = registrationNumber;
    }
    @OneToMany(mappedBy = "wasteTruck", cascade = CascadeType.ALL)
    private Set<Driver> drivers = new HashSet<>();
}

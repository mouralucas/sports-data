package com.rolf.sports_data.entities;

import com.rolf.sports_data.enums.CarClassEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 * RaceCarEntity
 * 
 * A car represent a entity, not a physical car
 * If a car is used for more than one pilot 
 *  (for competitions with only one pilot per car), 
 *  it will be duplicated here with different number
 */
@Entity 
@Table (name = "race_car")
public class RaceCarEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "constructor_id")
    private ConstructorEntity constructor;

    @Column (name = "number")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column (name = "car_class")
    private CarClassEnum car_class;

    @Column (name = "chassis")
    private String chassis;

    @Column(name = "year")
    private Integer year;
}

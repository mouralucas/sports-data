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
 * (for competitions with only one pilot per car),
 * it will be duplicated here with different number
 */
@Entity
@Table(name = "race_car")
public class RaceCarEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "constructor_id")
    private ConstructorEntity constructor;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_class")
    private CarClassEnum carClass;

    @Column(name = "chassis")
    private String chassis;

    @Column(name = "year")
    private Integer year;

    public ConstructorEntity getConstructor() {
        return constructor;
    }

    public void setConstructor(ConstructorEntity constructor) {
        this.constructor = constructor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CarClassEnum getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClassEnum car_class) {
        this.carClass = car_class;
    }

    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}

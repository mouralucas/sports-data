package com.rolf.sports_data.dto.raceCar;

public class RaceCarResponseDto {
    private Long id;
    private Long constructorId;
    private String constructorName;
    private String number;
    private String carClass;
    private String chassis;
    private Integer year;

    public RaceCarResponseDto(
            Long id,
            Long constructorId,
            String constructorName,
            String number,
            String carClass,
            String chassis, Integer year) {
        this.id = id;
        this.constructorId = constructorId;
        this.constructorName = constructorName;
        this.number = number;
        this.carClass = carClass;
        this.chassis = chassis;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(Long constructorId) {
        this.constructorId = constructorId;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
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

package com.rolf.sports_data.dto.contructor;

public class ConstructorResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer year;

    public ConstructorResponseDto(Long id, String name, String description, Integer year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}

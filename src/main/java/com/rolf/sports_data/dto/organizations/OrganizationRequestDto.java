package com.rolf.sports_data.dto.organizations;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class OrganizationRequestDto {

    
    private String name;

    private Long parentOrganizationId;

    private boolean active;

    @Min(0)
    private Integer page;

    @Min(0)
    @Max(100)
    private Integer size;

    private String sortBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(Long parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

     /* Setters and Getters */
     
}

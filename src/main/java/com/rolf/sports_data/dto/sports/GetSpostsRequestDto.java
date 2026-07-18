package com.rolf.sports_data.dto.sports;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class GetSpostsRequestDto {
    private String name;

    private Boolean status;

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

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

}

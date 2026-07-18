package com.rolf.sports_data.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;

public abstract class BaseSoftDeleteEntity extends BaseEntity {
    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private UUID deleted_by;
}

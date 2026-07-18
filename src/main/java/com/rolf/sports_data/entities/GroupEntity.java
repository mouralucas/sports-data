package com.rolf.sports_data.entities;

import jakarta.persistence.*;

import java.util.Set;

/**
 * League grouping, like in World Cuo: Group A, Group B or NFL NFC South, etc
 */
@Entity
@Table(name = "league_group")
public class GroupEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private GroupEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<GroupEntity> children;
}

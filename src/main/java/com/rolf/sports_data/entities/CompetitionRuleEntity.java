package com.rolf.sports_data.entities;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_rule", indexes = {
        @Index(name = "idx_competition_rule_template", columnList = "competition_template_id"),
        @Index(name = "idx_competition_rule_edition", columnList = "competition_edition_id") })
public class CompetitionRuleEntity extends BaseEntity {
    @Column(name = "scope_type", length = 20)
    private String scopeType;

    @ManyToOne
    @JoinColumn(name = "competition_template_id", nullable = true)
    private CompetitionTemplateEntity competitionTemplate;

    @ManyToOne
    @JoinColumn(name = "competition_edition_id", nullable = true)
    private CompetitionEditionEntity competitionEdition;

    @Column(name = "rule_version")
    private Integer ruleVersion;

    @Column(name = "configuration", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> configuration;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;
}

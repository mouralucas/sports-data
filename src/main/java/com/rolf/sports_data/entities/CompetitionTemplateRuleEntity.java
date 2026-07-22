package com.rolf.sports_data.entities;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.rolf.sports_data.enums.CompetitionRuleTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_template_rule", indexes = {
        @Index(name = "idx_competition_template_rule_competition_template", columnList = "competition_template_id"),
        @Index(name = "idx_competition_template_rule_competition_stage_template", columnList = "competition_stage_template_id"),
        @Index(name = "idx_competition_template_rule_edition", columnList = "competition_edition_id") })
public class CompetitionTemplateRuleEntity extends BaseEntity {
    @Column(name = "scope_type", length = 20)
    private String scopeType;

    @ManyToOne
    @JoinColumn(name = "competition_template_id", nullable = true)
    private CompetitionTemplateEntity competitionTemplate;

    @ManyToOne
    @JoinColumn(name = "competition_edition_id", nullable = true)
    private CompetitionEditionEntity competitionEdition;

    @ManyToOne
    @JoinColumn(name = "competition_stage_template_id", nullable = true)
    private CompetitionStageTemplateEntity competitionStageTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    private CompetitionRuleTypeEnum ruleType;

    @Column(name = "rule_version")
    private Integer ruleVersion;

    @Column(name = "configuration", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> configuration;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;
}

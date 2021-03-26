package com.example.redis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hero_skill_conditions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HeroSkillCondition {
    private Integer id;
    private String conditionName;
    private int heroId;
    @JsonIgnore
    private Hero hero;

    public HeroSkillCondition() {}

    public HeroSkillCondition(String conditionName, int heroId) {
        this.conditionName = conditionName;
        this.heroId = heroId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "condition_name")
    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    @Column(name = "hero_id")
    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hero_id", insertable = false, updatable = false)
    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}

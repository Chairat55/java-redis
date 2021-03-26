package com.example.redis.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "heros")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hero {
    private Integer id;
    private String heroName;
    private List<HeroSkill> heroSkills;
    private List<HeroSkillCondition> heroSkillConditions;
    private List<HeroItem> heroItems;
    private List<HeroExpTransaction> heroExpTransactions;

    public Hero() { }

    public Hero(String heroName) {
        this.heroName = heroName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "hero_name")
    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    @OneToMany(cascade = ALL, mappedBy = "hero")
    public List<HeroSkill> getHeroSkills() {
        return heroSkills;
    }

    public void setHeroSkills(List<HeroSkill> heroSkills) {
        this.heroSkills = heroSkills;
    }

    @OneToMany(cascade = ALL, mappedBy = "hero")
    public List<HeroSkillCondition> getHeroSkillConditions() {
        return heroSkillConditions;
    }

    public void setHeroSkillConditions(List<HeroSkillCondition> heroSkillConditions) {
        this.heroSkillConditions = heroSkillConditions;
    }

    @OneToMany(cascade = ALL, mappedBy = "hero")
    public List<HeroItem> getHeroItems() {
        return heroItems;
    }

    public void setHeroItems(List<HeroItem> heroItems) {
        this.heroItems = heroItems;
    }

    @OneToMany(cascade = ALL, mappedBy = "hero")
    public List<HeroExpTransaction> getHeroExpTransactions() {
        return heroExpTransactions;
    }

    public void setHeroExpTransactions(List<HeroExpTransaction> heroExpTransactions) {
        this.heroExpTransactions = heroExpTransactions;
    }
}

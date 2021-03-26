package com.example.redis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hero_skills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HeroSkill {
    private Integer id;
    private String skillName;
    private Integer heroId;
    @JsonIgnore
    private Hero hero;

    public HeroSkill() {}

    public HeroSkill(String skillName, int heroId) {
        this.skillName = skillName;
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

    @Column(name = "skill_name")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Column(name = "hero_id")
    public Integer getHeroId() {
        return heroId;
    }

    public void setHeroId(Integer heroId) {
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

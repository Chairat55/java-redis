package com.example.redis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hero_exp_transactions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HeroExpTransaction {
    private Integer id;
    private String description;
    private int exp;
    private int heroId;
    @JsonIgnore
    private Hero hero;

    public HeroExpTransaction() {}

    public HeroExpTransaction(String description, int exp, int heroId) {
        this.description = description;
        this.exp = exp;
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

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "exp")
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
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

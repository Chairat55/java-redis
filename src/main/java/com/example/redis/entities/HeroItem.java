package com.example.redis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "hero_items")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HeroItem {
    private Integer id;
    private String itemName;
    private int heroId;
    @JsonIgnore
    private Hero hero;

    public HeroItem() {}

    public HeroItem(String itemName, int heroId) {
        this.itemName = itemName;
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

    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

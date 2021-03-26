package com.example.redis.services;

import com.example.redis.entities.*;
import com.example.redis.repositories.HeroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    Jedis jedis = new Jedis("localhost");

    public void initHero () {
        for (int i = 1; i <= 5; i++) {
            Hero h = heroRepository.saveAndFlush(new Hero("HERO_" + i));

            List<HeroSkill> heroSkills = new ArrayList<>();
            List<HeroSkillCondition> heroSkillConditions = new ArrayList<>();
            List<HeroItem> heroItems = new ArrayList<>();
            List<HeroExpTransaction> heroExpTransactions = new ArrayList<>();

            for (int j = 1; j <= 5; j++) {
                heroSkills.add(new HeroSkill("SKILL_" + j, h.getId()));
                heroSkillConditions.add(new HeroSkillCondition("SKILL_CONDITION_" + j, h.getId()));
                heroItems.add(new HeroItem("ITEM_" + j, h.getId()));
                heroExpTransactions.add(new HeroExpTransaction("DESCRIPTION_" + j, j, h.getId()));
            }

            h.setHeroSkills(heroSkills);
            h.setHeroSkillConditions(heroSkillConditions);
            h.setHeroItems(heroItems);
            h.setHeroExpTransactions(heroExpTransactions);

            heroRepository.saveAndFlush(h);
        }
    }

    public void testRedis () throws InterruptedException {
        if (jedis.get("test-redis") == null) {
            jedis.set("test-redis", "TEST");
            Thread.sleep(5000);
        }
    }

    public List<Hero> list () throws InterruptedException, JsonProcessingException {
        List<Hero> heros;

        if (jedis.get("heros") == null) {
            heros = heroRepository.findAll();
            String str = new ObjectMapper().writeValueAsString(heros);
            jedis.set("heros", str);
            Thread.sleep(5000);

        } else {
            heros = new ObjectMapper().readValue(jedis.get("heros"), new TypeReference<List<Hero>>(){});
        }

        return heros;
    }

    public void create (Hero hero) throws JsonProcessingException {
        heroRepository.saveAndFlush(hero);

        if (jedis.get("heros") != null) {
            List<Hero> heros = new ObjectMapper().readValue(jedis.get("heros"), new TypeReference<List<Hero>>(){});
            heros.add(hero);

            String str = new ObjectMapper().writeValueAsString(heros);
            jedis.set("heros", str);
        }
    }

    public void update (int heroId, Hero hero) throws JsonProcessingException {
        Optional<Hero> exist = heroRepository.findById(heroId);
        if (!exist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hero not found !!");
        }

        exist.get().setHeroName(hero.getHeroName());
        heroRepository.saveAndFlush(exist.get());

        if (jedis.get("heros") != null) {
            List<Hero> heros = new ObjectMapper().readValue(jedis.get("heros"), new TypeReference<List<Hero>>(){});
            int index = heros.indexOf(heros.stream().filter(h -> h.getId() == heroId).findFirst().get());
            heros.set(index, exist.get());

            String str = new ObjectMapper().writeValueAsString(heros);
            jedis.set("heros", str);
        }
    }

    public void delete (int heroId, boolean updateRedis) throws JsonProcessingException {
        Optional<Hero> hero = heroRepository.findById(heroId);
        if (!hero.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hero not found !!");
        }
        heroRepository.delete(hero.get());

        if (jedis.get("heros") != null && updateRedis) {
            List<Hero> heros = new ObjectMapper().readValue(jedis.get("heros"), new TypeReference<List<Hero>>(){});
            heros.remove(heros.stream().filter(h -> h.getId() == heroId).findFirst().get());

            String str = new ObjectMapper().writeValueAsString(heros);
            jedis.set("heros", str);
        }
    }

}
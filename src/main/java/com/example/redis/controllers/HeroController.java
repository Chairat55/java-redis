package com.example.redis.controllers;

import com.example.redis.entities.Hero;
import com.example.redis.services.HeroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heros")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @GetMapping("test-redis")
    public String testRedis () throws InterruptedException {
        heroService.testRedis();
        return "SUCCESS";
    }

    @GetMapping
    public List<Hero> list () throws InterruptedException, JsonProcessingException {
        return heroService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create (
            @RequestBody Hero hero
    ) throws JsonProcessingException {
        heroService.create(hero);
    }

    @PutMapping("/{heroId}")
    public void update (
            @PathVariable("heroId") int heroId,
            @RequestBody Hero hero
    ) throws JsonProcessingException {
        heroService.update(heroId, hero);
    }

    @DeleteMapping("/{heroId}")
    public void delete (
            @PathVariable("heroId") int heroId,
            boolean updateRedis
    ) throws JsonProcessingException {
        heroService.delete(heroId, updateRedis);
    }

}

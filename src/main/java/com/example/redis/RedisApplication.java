package com.example.redis;

import com.example.redis.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class RedisApplication {

	@Autowired
	private HeroService heroService;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);

		System.out.println("\n");
		System.out.println("##############################################################");
		System.out.println("#                                                            #");
		System.out.println("#        H2 Console http://localhost:8080/h2-console         #");
		System.out.println("#                                                            #");
		System.out.println("##############################################################");
		System.out.println("\n");

		Jedis jedis = new Jedis("localhost");
		jedis.flushAll(); // Clear all data

		// Strings
		jedis.set("events/city/rome", "32,15,223,828");
		System.out.println("Get value from key 'events/city/rome' --> " + jedis.get("events/city/rome") + "\n");

		// Lists
		jedis.lpush("que", "1");
		jedis.lpush("que", "2");
		jedis.lpush("que", "3");
		jedis.rpop("que");

		List<String> ques = jedis.lrange("que", 0, -1);
		System.out.println("Get list of que --> " + ques + "\n");

		// Sets
		jedis.sadd("nicknames", "nickname#1");
		jedis.sadd("nicknames", "nickname#2");
		jedis.sadd("nicknames", "nickname#1");

		Set<String> nicknames = jedis.smembers("nicknames");
		boolean exists = jedis.sismember("nicknames", "nickname#1");
		System.out.println("Remove duplicate 'nicknames'  --> " + nicknames);
		System.out.println("Check exist 'nickname#1'  --> " + exists + "\n");

		// Hashes
		jedis.hset("user", "name", "Chairat");
		jedis.hset("user", "job", "Developer");

		String name = jedis.hget("user", "name");
		System.out.println("Get key 'user.name' from hash --> " + name);

		Map<String, String> user = jedis.hgetAll("user");
		System.out.println("Get all key from 'user' --> " + user + "\n");

		// Sorted Sets
		Map<String, Double> scores = new HashMap<>();

		scores.put("PlayerOne", 3000.0);
		scores.put("PlayerTwo", 1500.0);
		scores.put("PlayerThree", 8200.0);

		scores.entrySet().forEach(playerScore -> {
			jedis.zadd("ranking", playerScore.getValue(), playerScore.getKey());
		});

		String bestScore = jedis.zrevrange("ranking", 0, 1).iterator().next();
		System.out.println("Best score --> " + bestScore);

		long rank = jedis.zrevrank("ranking", "PlayerTwo");
		System.out.println("Find index of 'PlayerTwo' in 'ranking'  --> " + rank + "\n");

		// Set Expire
		jedis.expire("que", 60);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			heroService.initHero();
		};
	}

}

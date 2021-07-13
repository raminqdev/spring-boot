package com.raminq.bootifulredis;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Log4j2
@SpringBootApplication
public class BootifulRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootifulRedisApplication.class, args);
    }

    private ApplicationRunner titledRunner(String title, ApplicationRunner ar) {
        return args -> {
            log.info(title.toUpperCase() + "***********");
            ar.run(args);
        };
    }

    @Bean
    ApplicationRunner geography(RedisTemplate<String, String> redisTemplate) {
        return titledRunner("geography", args -> {
            GeoOperations<String, String> geo = redisTemplate.opsForGeo();
            geo.add("Tehran", new Point(13.3432, 38.11445), "Apache");
            geo.add("Tehran", new Point(15.9873, 37.23980), "Catalinia");
            geo.add("Tehran", new Point(13.3432, 37.3444), "Tomcat");

            Circle circle = new Circle(new Point(13.582332, 37.31977889),
                    new Distance(100, RedisGeoCommands.DistanceUnit.KILOMETERS));

            GeoResults<RedisGeoCommands.GeoLocation<String>> tehranResults = geo.radius("Tehran", circle);
            tehranResults.getContent().forEach(c->log.info(c.toString()));
        });
    }


}

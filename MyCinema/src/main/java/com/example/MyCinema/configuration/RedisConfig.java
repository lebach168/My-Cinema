package com.example.MyCinema.configuration;


import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPooled;

import java.time.Duration;


@Configuration
@EnableMBeanExport(registration= RegistrationPolicy.IGNORE_EXISTING)
public class RedisConfig {
    @NonFinal
    @Value("${REDIS_URL}")
    private String redisURL;

    @Bean
    public JedisPoolConfig config(){
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
// maximum active connections in the pool,
// tune this according to your needs and application type
// default is 8
        poolConfig.setMaxTotal(128);
// maximum idle connections in the pool, default is 8
        poolConfig.setMaxIdle(128);
// minimum idle connections in the pool, default 0
        poolConfig.setMinIdle(16);
// Enables waiting for a connection to become available.
        poolConfig.setBlockWhenExhausted(true);
// The maximum number of seconds to wait for a connection to become available
        poolConfig.setMaxWait(Duration.ofSeconds(1));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
// Enables sending a PING command periodically while the connection is idle.
        poolConfig.setTestWhileIdle(true);
// controls the period between checks for idle connections in the pool
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(1));
// JedisPooled does all hard work on fetching and releasing connection to the pool
// to prevent connection starvation
        return poolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig config){
        return new JedisPool(config, redisURL);
    }
}

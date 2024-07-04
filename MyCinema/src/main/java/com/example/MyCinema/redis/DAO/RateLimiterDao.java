package com.example.MyCinema.redis.DAO;

import com.example.MyCinema.exception.RateLimitExceededException;
import com.example.MyCinema.redis.RedisSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Component
public class RateLimiterDao {
    private final int interval = 60;
    private final int expiration = 3600;
    private final long maxHits = 5;
    private final JedisPool jedisPool;


    public void hit(long userId){
        int dayHourInterval = getMinuteOfDayBlock(ZonedDateTime.now());
        try(Jedis jedis = jedisPool.getResource()){
            String key = RedisSchema.getRateLimiterKey(userId,dayHourInterval);
            Pipeline pipeline = jedis.pipelined();
            Response<Long> hits = pipeline.incr(key);
            pipeline.expire(key, expiration);
            pipeline.sync();
            if (hits.get() > maxHits) {
                throw new RateLimitExceededException("Rate Limit Exceeded");

            }
        }
    }
    private int getMinuteOfDayBlock(ZonedDateTime dateTime) {
        int minuteOfDay = dateTime.getHour() * 60 + dateTime.getMinute();
        return minuteOfDay / interval;
    }
}

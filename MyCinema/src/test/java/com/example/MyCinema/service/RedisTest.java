package com.example.MyCinema.service;

import com.example.MyCinema.dto.response.SeatResponse;
import com.example.MyCinema.model.Cinema;
import com.example.MyCinema.model.Room;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.model.SeatType;
import com.example.MyCinema.redis.RedisSchema;
import lombok.experimental.NonFinal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.ScanResult;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
public class RedisTest {
    @NonFinal
    @Value("${REDIS_URL}")
    String redisUrl;
    @Test
    void testConnection() {
        JedisPool jedisPool = new JedisPool(redisUrl);
        try(Jedis jedis = jedisPool.getResource() ){
            Pipeline p= jedis.pipelined();
            Seat seat = Seat.builder()
                    .rowName("A")
                    .room(new Room())
                    .number(1)
                    .type(SeatType.STANDARD)
                    .id(10)
                    .build();
            SeatResponse seatResponse = new SeatResponse(seat);
            Response<String> status = p.get(RedisSchema.getSeatStatusKey(21, seat.getId()));
            p.sync();

            seatResponse.setStatus(status.get() == null ? "Available":status.get());
            System.out.println(seatResponse.toString());

        }
    }
    @Test
    public void testDataStructure(){
        Jedis jedis = new Jedis(redisUrl);
    }
}

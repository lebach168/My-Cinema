package com.example.MyCinema.redis.DAO;

import com.example.MyCinema.dto.request.BookingRequest;
import com.example.MyCinema.exception.SeatAlreadyReservedException;
import com.example.MyCinema.redis.RedisSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationDao {
    private final JedisPool jedisPool;
    private final int expiration = 300;
    public void createReservation(Long userId, Long showtimeId, List<Long> seatIds){
        List<String> createdKey = new ArrayList<>();
        try(Jedis jedis = jedisPool.getResource()){
            for(Long seatId:seatIds){
                String key = RedisSchema.getSeatStatusKey(showtimeId,seatId);
                String isExisted = jedis.get(key);
                if(isExisted == null){
                    Transaction t = jedis.multi();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("user",userId.toString());
                    map.put("state","Pending");
                    jedis.hset(key, map);
                    jedis.expire(key,expiration);
                    t.exec();
                    createdKey.add(key);
                }
                else{
                    Pipeline p = jedis.pipelined();
                    for(String k: createdKey){
                        p.del(k);
                    }
                    p.sync();
                    throw new SeatAlreadyReservedException("Seat already reserved!");

                }

            }

        }
    }
}

package com.example.MyCinema.redis.DAO;

import com.example.MyCinema.dto.request.ShowtimeDTO;
import com.example.MyCinema.dto.response.SeatResponse;
import com.example.MyCinema.model.Seat;
import com.example.MyCinema.redis.RedisSchema;
import com.example.MyCinema.repository.SeatRepository;
import com.example.MyCinema.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ShowtimeDao {
    private final SeatRepository seatRepository;
    private final JedisPool jedisPool;

    public Set<SeatResponse> getCurrentState(ShowtimeDTO showtimeDto){
        Set<SeatResponse> seats = loadAllSeatIdToRedisIfNotExists(showtimeDto);
        try(Jedis jedis = jedisPool.getResource()){
            Map<SeatResponse, Response<String>> responseList = new HashMap<>();
            Pipeline p = jedis.pipelined();
            for(SeatResponse seat: seats){
                Response<String> getState = p.hget(RedisSchema.getSeatStatusKey(showtimeDto.getShowtimeId(),seat.getId()),"state");
                responseList.put(seat,getState);
            }
            p.sync();
            for(Map.Entry<SeatResponse, Response<String>> entry:  responseList.entrySet()){
                SeatResponse seat = entry.getKey();
                String state= entry.getValue().get();
                seat.setStatus(state==null?"Available":state);
            }
        }
        return seats;
    }
    public Set<SeatResponse> loadAllSeatIdToRedisIfNotExists(ShowtimeDTO showtimeDto){
        String roomKey = RedisSchema.getRoomKey(showtimeDto.getCinemaId(), showtimeDto.getRoomId());
        Set<SeatResponse> seats = new HashSet<>();
        try(Jedis jedis = jedisPool.getResource()){
            if(!jedis.exists(roomKey)){
                List<Seat> seatList = seatRepository.findAllByRoomId(showtimeDto.getRoomId());
                Transaction t = jedis.multi();
                for(Seat seat:seatList){
                    t.sadd(roomKey,String.valueOf(seat.getId()));
                    SeatResponse seatResponse = new SeatResponse(seat);
                    seats.add(seatResponse);
                    t.hset(RedisSchema.getSeatKey(seat.getId()),seatResponse.toMap());
                }
                t.exec();

            }
            else {
                Set<String> seatIds = jedis.smembers(roomKey);
                int length = seatIds.size();
                Pipeline p = jedis.pipelined();
                List<Response<Map<String, String>>> responseList = new ArrayList<>();
                for(String seatId:seatIds){
                    Response<Map<String, String>> response = p.hgetAll(RedisSchema.getSeatKey(seatId));
                    responseList.add(response);
                }
                p.sync();
                for(Response<Map<String, String>> response:responseList){
                    seats.add(new SeatResponse(response.get()));
                }
            }
        }
        return seats;
    }
}

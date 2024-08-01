package com.example.MyCinema.redis;

import com.example.MyCinema.model.redisModel.Reservation;

public class RedisSchema {
    //sample: Showtime:1:Seat:10
    public static String getReservationHashKey(Reservation reservation){
        return "showtime:"+reservation.getShowtimeId()+":seat:"+reservation.getSeatId();
    }
    public static String getSeatStatusKey(long showtimeId, long seatId){
        return "showtime:"+ showtimeId + ":seat:"+seatId;
    }
    public static String getSeatStatusKey(long showtimeId, String seatId){
        return "showtime:"+ showtimeId + ":sat:"+seatId;
    }
    public static String getRateLimiterKey(long userId, int interval){
        return "limiter:user:"+ userId+":"+interval;
    }
    //sample: Showtime:1:*
    public static String getShowtimeKeyPattern(long showtimeId){
        return "showtime:"+showtimeId+":*";
    }
    public static String getRoomKey(long cinemaId, long roomId){
        return "cinema:"+cinemaId+":Room:"+roomId;
    }
    public static String getSeatKey(long seatId){
        return "seat:"+seatId;
    }
    public static String getSeatKey(String seatId){
        return "seat:"+seatId;
    }
}

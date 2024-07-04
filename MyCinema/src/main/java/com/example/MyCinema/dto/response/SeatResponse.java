package com.example.MyCinema.dto.response;

import com.example.MyCinema.model.Seat;
import com.example.MyCinema.model.SeatType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@ToString
public class SeatResponse {
    Long id;
    String name;//row name + number
    String type;

    String status;// available, pending, booked, blocked,
    public SeatResponse(){}
    public SeatResponse(Seat seat){
        this.id = seat.getId();
        this.name = seat.toStringName();
        this.type = seat.getType().toString();
        this.status=null;
    }
    public SeatResponse(Map<String, String> fields){
        this.id  = Long.valueOf(fields.getOrDefault("id", null));
        this.name = fields.getOrDefault("name",null);
        this.type = fields.getOrDefault("type",null);
        this.status = null;
    }
    public Map<String, String> toMap(){
        Map<String, String> object = new HashMap<>();
        object.put("id", this.id.toString());
        object.put("name", this.name);
        object.put("type", this.type);
        return object;
    }
}

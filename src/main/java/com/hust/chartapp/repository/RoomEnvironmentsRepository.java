package com.hust.chartapp.repository;

import com.hust.chartapp.domain.RoomEnvironments;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class RoomEnvironmentsRepository extends BaseRepository<RoomEnvironments, String> {

    @Override
    public RoomEnvironments doSthToObj(Object input, String s, RoomEnvironments obj) {
        try {
            obj.setDateObj(new SimpleDateFormat("dd:MM:yyyyHH:mm:ss").parse(obj.getDate() + obj.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

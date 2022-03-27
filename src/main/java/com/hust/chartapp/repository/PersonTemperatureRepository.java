package com.hust.chartapp.repository;

import com.hust.chartapp.domain.PersonTemperature;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Component;

@Component
public class PersonTemperatureRepository extends BaseRepository<PersonTemperature, String> {

    @Override
    public PersonTemperature doSthToObj(Object input, String s, PersonTemperature obj) {
        try {
            obj.setDateObj(new SimpleDateFormat("dd:MM:yyyyHH:mm:ss").parse(obj.getDate() + obj.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

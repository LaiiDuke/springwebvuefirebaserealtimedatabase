package com.hust.chartapp.repository;

import com.hust.chartapp.domain.PersonHealth;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.stereotype.Repository;

@Repository
public class PersonHealthRepository extends BaseRepository<PersonHealth, String> {

    @Override
    public PersonHealth doSthToObj(Object input, String s, PersonHealth obj) {
        try {
            obj.setDateObj(new SimpleDateFormat("dd:MM:yyyyHH:mm:ss").parse(obj.getDate() + obj.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

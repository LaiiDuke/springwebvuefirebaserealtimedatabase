package com.hust.chartapp.web.rest;

import com.hust.chartapp.domain.PersonHealth;
import com.hust.chartapp.domain.PersonTemperature;
import com.hust.chartapp.repository.PersonTemperatureRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-temperature")
public class PersonTemperatureController {

    private List<PersonTemperature> globalList = new ArrayList<>();

    public PersonTemperatureController(PersonTemperatureRepository personTemperatureRepository) {
        this.personTemperatureRepository = personTemperatureRepository;
    }

    public List<PersonTemperature> getGlobalList() {
        this.globalList = this.personTemperatureRepository.getAll();
        return globalList;
    }

    private final PersonTemperatureRepository personTemperatureRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<PersonTemperature>> all() {
        Collections.sort(getGlobalList());
        return ResponseEntity.ok(globalList);
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public ResponseEntity<PersonTemperature> latest() {
        Collections.sort(getGlobalList());
        return ResponseEntity.ok(globalList.get(0));
    }
}

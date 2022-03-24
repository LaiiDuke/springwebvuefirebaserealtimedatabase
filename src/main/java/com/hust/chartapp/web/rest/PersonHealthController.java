package com.hust.chartapp.web.rest;

import com.hust.chartapp.domain.PersonHealth;
import com.hust.chartapp.repository.PersonHealthRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-health")
public class PersonHealthController {

    private List<PersonHealth> globalList = new ArrayList<>();

    public List<PersonHealth> getGlobalList() {
        this.globalList = this.personHealthRepository.getAll();
        return globalList;
    }

    private final PersonHealthRepository personHealthRepository;

    public PersonHealthController(PersonHealthRepository personHealthRepository) {
        this.personHealthRepository = personHealthRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<PersonHealth>> all() {
        return ResponseEntity.ok(getGlobalList());
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public ResponseEntity<PersonHealth> latest() {
        Collections.sort(getGlobalList());
        return ResponseEntity.ok(globalList.get(0));
    }
}

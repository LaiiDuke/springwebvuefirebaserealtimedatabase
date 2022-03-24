package com.hust.chartapp.web.rest;

import com.hust.chartapp.domain.PersonHealth;
import com.hust.chartapp.repository.PersonHealthRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/person-health")
public class PersonHealthController {

    @Autowired
    private PersonHealthRepository personHealthRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<PersonHealth>> all() {
        return ResponseEntity.ok(personHealthRepository.getAll());
    }
}

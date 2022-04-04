package com.hust.chartapp.web.rest;

import com.hust.chartapp.domain.PersonTemperature;
import com.hust.chartapp.domain.RoomEnvironments;
import com.hust.chartapp.repository.RoomEnvironmentsRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/room-environments")
public class RoomEnvironmentsController {

    private List<RoomEnvironments> globalList = new ArrayList<>();

    public RoomEnvironmentsController(RoomEnvironmentsRepository roomEnvironmentsRepository) {
        this.roomEnvironmentsRepository = roomEnvironmentsRepository;
    }

    public List<RoomEnvironments> getGlobalList() {
        this.globalList = this.roomEnvironmentsRepository.getAll();
        return globalList;
    }

    private final RoomEnvironmentsRepository roomEnvironmentsRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<RoomEnvironments>> all() {
        Collections.sort(getGlobalList());
        return ResponseEntity.ok(globalList);
    }

    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    public ResponseEntity<RoomEnvironments> latest() {
        Collections.sort(getGlobalList());
        return ResponseEntity.ok(globalList.get(0));
    }
}

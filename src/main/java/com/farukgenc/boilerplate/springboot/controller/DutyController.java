package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.Duty;
import com.farukgenc.boilerplate.springboot.repository.DutyRepository;
import com.farukgenc.boilerplate.springboot.repository.EntryRepository;
import com.farukgenc.boilerplate.springboot.service.DutyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/duty")

public class DutyController {
    private final DutyRepository dutyRepository;
    private final DutyService dutyService;
    private final EntryRepository entryRepository;

    public DutyController(DutyRepository dutyRepository, DutyService dutyService,
                          EntryRepository entryRepository) {
        this.dutyRepository = dutyRepository;
        this.dutyService = dutyService;
        this.entryRepository = entryRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Duty>> getAllDuties() {
        return ResponseEntity.ok(dutyRepository.findAll());
    }

    @PostMapping("/create/{firstUserId}/{secondUserid}/{startTime}/{endTime}")
    public ResponseEntity<String> createDuty(@PathVariable Long secondUserid, @PathVariable Long firstUserId, @PathVariable String startTime, @PathVariable String endTime, @RequestBody Duty duty) {
        return ResponseEntity.ok(dutyService.createDuty(firstUserId, secondUserid, startTime, endTime, duty));
    }

    @PostMapping("/start/{secondGuardId}")
    public ResponseEntity<String> startDuty(@PathVariable Long secondGuardId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(dutyService.startDuty(token, secondGuardId));
    }

    @PostMapping("/end")
    public ResponseEntity<String> endDuty() {
        return ResponseEntity.ok(dutyService.endDuty());
    }

    @PostMapping("/isuserduty")
    public ResponseEntity<Boolean> isUserDuty(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return ResponseEntity.ok(dutyService.isUserDuty(token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDuty(@PathVariable Long id) {
        return ResponseEntity.ok(dutyService.deleteDuty(id));
    }
}

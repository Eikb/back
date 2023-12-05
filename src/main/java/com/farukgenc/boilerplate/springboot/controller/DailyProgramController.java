package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.dailyprogram.DailyProgram;
import com.farukgenc.boilerplate.springboot.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Author Enes Ilkbay
// Created: 02.11.2023
// This class is used to show the daily Program like the time and the name od the actual lesson


@CrossOrigin
@RestController
public class DailyProgramController {
    private final ProgramService programService;

    public DailyProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/program")
    public ResponseEntity<List<DailyProgram>> getListOfDailyProgram(){
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @PostMapping("/program/create")
    public ResponseEntity<String> createProgram(@RequestBody DailyProgram dailyProgram){
        dailyProgram.setActive(false);
        return ResponseEntity.ok(programService.createProgram(dailyProgram));
    }

    @PostMapping("/program/{programId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long programId, @RequestBody DailyProgram dailyProgram){
        return ResponseEntity.ok(programService.updateProgram(programId, dailyProgram));
    }


    @DeleteMapping("/program/{dailyProgramId}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long dailyProgramId){
        return ResponseEntity.ok(programService.deleteProgram(dailyProgramId));
    }

}

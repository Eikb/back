package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.User;
import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import com.farukgenc.boilerplate.springboot.model.enterExit.Student;
import com.farukgenc.boilerplate.springboot.repository.DutyRepository;
import com.farukgenc.boilerplate.springboot.repository.EntryRepository;
import com.farukgenc.boilerplate.springboot.repository.StudentRepository;
import com.farukgenc.boilerplate.springboot.repository.UserRepository;
import com.farukgenc.boilerplate.springboot.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/entry")
public class EntryController {

    private  final EntryService entryService;
    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final EntryRepository entryRepository;

    public EntryController(EntryService entryService,
                           DutyRepository dutyRepository,
                           UserRepository userRepository,
                           StudentRepository studentRepository,
                           EntryRepository entryRepository) {
        this.entryService = entryService;
        this.dutyRepository = dutyRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.entryRepository = entryRepository;
    }

    @GetMapping("/{day}")
    public ResponseEntity<List<Entry>> getAllEntriesOfTheDay(@PathVariable String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dayInDateTimeFormat = LocalDateTime.parse(day, formatter);
        return ResponseEntity.ok(entryService.getAllEntriesOfTheDay(dayInDateTimeFormat));
    }

    @PostMapping("/{day}")
    public ResponseEntity<String> createEntry(@RequestBody Entry entry, @PathVariable String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dayInDateTimeFormat = LocalDateTime.parse(day, formatter);
        entry.setExit(dayInDateTimeFormat);
        entry.setDutyId(dutyRepository.findByActive(true).getId());
        Student student = studentRepository.findByName(entry.getStudentName());


        return ResponseEntity.ok(entryService.createEntry(entry));
    }

    @PostMapping("/collection/{day}")
    public ResponseEntity<String> createCollectionOfEntries(@RequestBody List<Entry> entries, @PathVariable String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dayInDateTimeFormat = LocalDateTime.parse(day, formatter);

        AtomicInteger error = new AtomicInteger();
        entries.stream().map(e -> {
            Student student = studentRepository.findByName(e.getStudentName());
            e.setExit(dayInDateTimeFormat);
            if(dutyRepository.findByActive(true) == null){
               error.getAndIncrement();
            }else {
                e.setDutyId(dutyRepository.findByActive(true).getId());
                studentRepository.updateInBuildingById(false,student.getId());
                entryService.createEntry(e);
            }

            return "f";
        }).collect(Collectors.toList());

        if(error.get() > 0){
            return ResponseEntity.status(300).body("Aktif Nöbet bulunamadi. Önce Nöbete Giris yapiniz");
        }
        return ResponseEntity.ok("List Kayit edildi");
    }

    @GetMapping("/{id}/{day}")
    public ResponseEntity<String> editEntry(@PathVariable String day, @PathVariable Long id){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dayInDateTimeFormat = LocalDateTime.parse(day, formatter);
        return ResponseEntity.ok(entryService.editEnter(dayInDateTimeFormat, id));
    }
    @PostMapping("/collection/exit/{day}")
    public ResponseEntity<String> editCollectionOfEntries(@RequestBody List<Long> exitsId, @PathVariable String day){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dayInDateTimeFormat = LocalDateTime.parse(day, formatter);

        exitsId.stream().map(e -> {
            Entry entry = entryRepository.findById(e).get();
            entry.setEnter(dayInDateTimeFormat);
            Student student = studentRepository.findByName(entry.getStudentName());
            entry.setDutyId(dutyRepository.findByActive(true).getId());
            studentRepository.updateInBuildingById(true, student.getId());
            entryService.editEnter(dayInDateTimeFormat, entry.getId());
            return "f";
        }).collect(Collectors.toList());

        return ResponseEntity.ok("List Kayit edildi");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long id){
        return ResponseEntity.ok(entryService.deleteEnter(id));
    }

    @GetMapping("/statistics/rounds/{reason}")
    public ResponseEntity<Map<String, Long>> getRoundStatistics(@PathVariable String reason) {
        Map<String, Long> roundStatistics = entryService.generateRoundStatistics(reason);
        return ResponseEntity.ok(roundStatistics);
    }
}

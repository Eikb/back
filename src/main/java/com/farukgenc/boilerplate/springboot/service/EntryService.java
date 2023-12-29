package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import com.farukgenc.boilerplate.springboot.model.enterExit.Student;
import com.farukgenc.boilerplate.springboot.repository.EntryRepository;
import com.farukgenc.boilerplate.springboot.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EntryService {
    private final StudentRepository studentRepository;

    private final  EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository,
                        StudentRepository studentRepository) {
        this.entryRepository = entryRepository;
        this.studentRepository = studentRepository;
    }

    public List<Entry> getAllEntriesOfTheDay(LocalDateTime localDateTime){
       List<Entry> entries = entryRepository.findAll();

        return entries.stream()
                .filter(e -> (e.getExit().getDayOfMonth() == localDateTime.getDayOfMonth() && e.getExit().getMonth() == localDateTime.getMonth() && e.getExit().getYear() == localDateTime.getYear()) || Objects.isNull(e.getEnter()) || e.getEnter().getDayOfMonth() == localDateTime.getDayOfMonth() && e.getEnter().getMonth() == localDateTime.getMonth() && e.getEnter().getYear() == localDateTime.getYear())
                .sorted(Comparator.comparing(e -> e.getExit())) // Hier wird die Sortierung nach der Exit-Uhrzeit durchgeführt
                .collect(Collectors.toList());

    }

    public String createEntry(Entry entry){
        try {
            entryRepository.save(entry);
            return "Kayit edilidi";
        }catch (Exception e){
            return "Hata olusud" + e.getMessage();
        }
    }
    public String editEnter(LocalDateTime dateTime, Long id){
        try {
            String studentName = entryRepository.findById(id).get().getStudentName();
            studentRepository.updateInBuildingById(true, studentRepository.findByName(studentName).getId());
            entryRepository.updateEnterById(dateTime, id);
            return "Update edilidi";
        }catch (Exception e){
            return "Hata olusudu" + e.getMessage();

        }
    }

    public String deleteEnter(Long id){
        try {
            entryRepository.deleteById(id);
            return "Silindi";
        }catch (Exception e){
            return "Silinemedi: " + e.getMessage();
        }
    }

    public Map<String, Long> generateRoundStatistics(String statisticReason) {
        List<Entry> entries = entryRepository.findAll();

        return entries.stream()
                .filter(entry -> statisticReason.toLowerCase().equals(entry.getReason().toLowerCase())) // Filtern nach dem Grund "Rund rausgegangen"
                .collect(Collectors.groupingBy(Entry::getStudentName, Collectors.counting()));
    }

    public List<Entry> getAllEntriesOfOneStudent(Long studentId){
       return entryRepository.findAll().stream().filter((e) -> {
            studentRepository.findById(studentId);
           return Objects.equals(e.getStudentName(), studentRepository.findById(studentId).get().getName());
       }).collect(Collectors.toList());
    }


}

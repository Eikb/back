package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.dailyprogram.DailyProgram;
import com.farukgenc.boilerplate.springboot.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public List<DailyProgram> getAllPrograms(){
       return programRepository.findAll();
    }

    public String createProgram(DailyProgram dailyProgram){
        try{
            programRepository.save(dailyProgram);
            return "Kayid edilmisdir";
        }catch (Exception e){
            return "Bir Hata olusmustur: " + e.getMessage();
        }
    }

    public String createProgramList(DailyProgram dailyProgram, List<Integer> days){
        try{
            List<DailyProgram> dailyProgramList = days.stream().map(e -> {
                DailyProgram dailyProgram1 = new DailyProgram();
                dailyProgram1.setCourseId(dailyProgram.getCourseId());
                dailyProgram1.setStartTime(dailyProgram.getStartTime());
                dailyProgram1.setEndTime(dailyProgram.getEndTime());
                dailyProgram1.setColor(dailyProgram.getColor());
                dailyProgram1.setDay(e);
                return dailyProgram1;
            }).collect(Collectors.toList());
            programRepository.saveAll(dailyProgramList);
            return "Kayid edilmisdir";
        }catch (Exception e){
            return "Bir Hata olusmustur: " + e.getMessage();
        }
    }

    public String updateProgram(Long programId, DailyProgram dailyProgram){
        try{
            programRepository.updateCourseIdAndDayAndStartTimeAndEndTimeAndColorById(dailyProgram.getCourseId(), dailyProgram.getDay(),dailyProgram.getStartTime(),dailyProgram.getEndTime(), dailyProgram.getColor(), programId);
            return "Degisti";
        }catch (Exception e){
            return "Bir Hata olusmustur: " +e.getMessage();
        }
    }

    public String deleteProgram(Long dailyProgramId){
        try{
            programRepository.deleteById(dailyProgramId);
            return "Silindi";
        }catch (Exception e){
            return "Bir Hata olusmustur: " + e.getMessage();
        }
    }
}

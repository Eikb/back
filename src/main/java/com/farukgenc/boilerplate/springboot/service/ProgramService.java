package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.dailyprogram.DailyProgram;
import com.farukgenc.boilerplate.springboot.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;

    public List<DailyProgram> getAllPrograms(){
       return programRepository.findAllByOrderByTime();
    }

    public String createProgram(DailyProgram dailyProgram){
        try{
            programRepository.save(dailyProgram);
            return "Kayid edilmisdir";
        }catch (Exception e){
            return "Bir Hata olusmustur: " + e.getMessage();
        }
    }

    public String updateProgram(Long programId, DailyProgram dailyProgram){
        try{
            programRepository.updateProgramAndTimeById(dailyProgram.getProgram(), dailyProgram.getTime(), programId);
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

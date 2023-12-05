package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Duty;
import com.farukgenc.boilerplate.springboot.model.User;
import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import com.farukgenc.boilerplate.springboot.repository.DutyRepository;
import com.farukgenc.boilerplate.springboot.repository.EntryRepository;
import com.farukgenc.boilerplate.springboot.repository.UserRepository;
import com.farukgenc.boilerplate.springboot.security.jwt.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DutyService {
    private final EntryRepository entryRepository;
    private final EntryService entryService;
    private final UserRepository userRepository;
    private final DutyRepository dutyRepository;
    private final JwtTokenManager jwtTokenManager;


    public String createDuty(Long firsUserId, Long secondUserId, String startDateTime, String endDateTime, Duty duty){
            User firstGuard = userRepository.findById(firsUserId).get();
            User secondGuard = userRepository.findById(secondUserId).get();

            //Build  Duty
            Duty createdDuty = new Duty();
            createdDuty.setActive(false);
            createdDuty.setCompleted(false);
            createdDuty.setFirstGuardId(firstGuard.getId());
            createdDuty.setSecondGuardId(secondGuard.getId());
            createdDuty.setFirstGuardName(firstGuard.getName());



            //Format Date and Time (dd-MM-yyyy HH:mm) -> (yyyy-mm-ddTHH:mm:ss)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            LocalDateTime dateTimeRightStart = LocalDateTime.parse(startDateTime, formatter);
            createdDuty.setStartTime(dateTimeRightStart);

            LocalDateTime dateTimeRightEnd = LocalDateTime.parse(endDateTime, formatter);
            createdDuty.setEndTime(dateTimeRightEnd);
            dutyRepository.save(createdDuty);

            //Add Duty to the Duty List of Guards
            userRepository.updateDutyStartTimeById(dateTimeRightStart, firsUserId);
            userRepository.updateDutyEndTimeById(dateTimeRightEnd, firsUserId);

            userRepository.updateDutyStartTimeById(dateTimeRightStart, secondUserId);
            userRepository.updateDutyEndTimeById(dateTimeRightEnd, secondUserId);
            return "Kayid edildi";
        }
        
        public String startDuty(String token, Long secondGuardId){
            try {
                //find Guards
                User firstGuard = userRepository.findByUsername(jwtTokenManager.getUsernameFromToken(token.split(" ")[1]));
                User secondGuard = userRepository.findById(secondGuardId).get();
                if(Objects.nonNull(dutyRepository.findByActive(true))){
                    return "Bir tane Oturum acik. Ilk olarak onu kapatin";
                }
                Duty duty = new Duty();
                duty.setStartedTime(LocalDateTime.now());
                duty.setActive(true);
                duty.setCompleted(false);
                duty.setFirstGuardId(firstGuard.getId());
                duty.setSecondGuardId(secondGuard.getId());
                duty.setStartedTime(LocalDateTime.now());
                duty.setFirstGuardName(firstGuard.getName());
                duty.setSecondGuardName(secondGuard.getName());
                dutyRepository.save(duty);

                userRepository.updateDuty(true,duty.getFirstGuardId());
                userRepository.updateDuty(true,duty.getSecondGuardId());

                return "Nöbet baslamistir";
            }catch (Exception e){
                return "Nöbet bulunamadi: " + e.getMessage();
            }

        }

    public String endDuty(){
        try {
            Duty duty = dutyRepository.findByActive(true);
            List<Entry> entryList = entryService.getAllEntriesOfTheDay(LocalDateTime.now());
            userRepository.updateDuty(false,duty.getFirstGuardId());
            userRepository.updateDuty(false,duty.getSecondGuardId());
            dutyRepository.updateActiveById(false,duty.getId());
            dutyRepository.updateCompletedTimeById(LocalDateTime.now(), duty.getId());
            dutyRepository.updateCompletedTimeAndCompletedAndActiveById(LocalDateTime.now(),true, false, duty.getId());
            duty.setEntries(entryList);
            dutyRepository.save(duty);
            return "Nöbet bitmistir";
        }catch (Exception e){
            return "Nöbet bulunamadi: " + e.getMessage();
        }
    }
        public Boolean isUserDuty(String token){
            Duty duty = dutyRepository.findByActive(true);
            if(duty == null){
                return false;
            }
            User firstGuard = userRepository.findByUsername(jwtTokenManager.getUsernameFromToken(token.split(" ")[1]));

            return Objects.equals(duty.getFirstGuardId(), firstGuard.getId()) || Objects.equals(duty.getSecondGuardId(), firstGuard.getId());
        }
    public List<Duty> getAllDuties(){
       return dutyRepository.findAll();
    }

    public String deleteDuty(Long id) {
        try {
            dutyRepository.deleteById(id);
            return "SILINDI";
        }catch (Exception e){
            return "Hata: " + e.getMessage();
        }
    }


}

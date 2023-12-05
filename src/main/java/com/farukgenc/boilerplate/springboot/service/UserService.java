package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Duty;
import com.farukgenc.boilerplate.springboot.model.User;
import com.farukgenc.boilerplate.springboot.repository.DutyRepository;
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
public class UserService {
    private final DutyRepository dutyRepository;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final DutyService dutyService;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }


    public String setDuty(Long userId, Boolean duty, Long secondUserId){
      if(duty){
          if(!Objects.isNull(dutyRepository.findByActive(true))){
              return "Acik bir tane Nöbet mevcut. Ilk onu kapatiniz";
          }
          userRepository.updateDuty(true,userId);
          userRepository.updateDuty(true,secondUserId);

          Duty createdDuty = new Duty();
          createdDuty.setStartedTime(LocalDateTime.now());
          createdDuty.setFirstGuardId(userRepository.findById(userId).get().getId());
          createdDuty.setSecondGuardId(userRepository.findById(secondUserId).get().getId());
          createdDuty.setActive(true);
          dutyRepository.save(createdDuty);
          return "Nöbet Acildi";

      }else {
          Duty duty1 = dutyRepository.findByActive(true);

          userRepository.updateDuty(false,userId);
          userRepository.updateDuty(false,duty1.getSecondGuardId());
          dutyRepository.updateActiveById(false,duty1.getId());


         dutyRepository.updateCompletedTimeById(LocalDateTime.now(),duty1.getId());
          return "Nöbet Kapandi";

      }
    }
    public Boolean isUserDuty(Long userId){
        User user = userRepository.findById(userId).get();
        return user.getDuty();
    }

    public String getDutyStartTime(Long userId){

        try {
            User user = userRepository.findById(userId).get();
            return user.getDutyStartTime().toString();
        }
        catch (Exception e){
            return "NaN";
        }
    }
    public String getDutyEndTime(Long userId){
        try {
            User user = userRepository.findById(userId).get();
            return user.getDutyEndTime().toString();
        }
      catch (Exception e){
            return "NaN";
      }
    }

    public String setDutyStartTime(Long userId, String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTimeRight = LocalDateTime.parse(dateTime, formatter);
        userRepository.updateDutyStartTime(dateTimeRight, userId);
        return "Eklenmistir";
    }
    public String setDutyEndTime(Long userId, String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTimeRight = LocalDateTime.parse(dateTime, formatter);
        userRepository.updateDutyEndTime(dateTimeRight, userId);
        return "Eklenmistir";
    }



}

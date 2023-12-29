package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.User;
import com.farukgenc.boilerplate.springboot.model.UserRole;
import com.farukgenc.boilerplate.springboot.repository.UserRepository;
import com.farukgenc.boilerplate.springboot.security.jwt.JwtTokenManager;
import com.farukgenc.boilerplate.springboot.service.UserService;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping()
    public ResponseEntity<Object> getUserByToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token ){
      User user = userRepository.findByUsername(jwtTokenManager.getUsernameFromToken(token));
       user.setPassword("");
      return ResponseEntity.ok(user);
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
       return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Erfolgreich");
        }catch (Exception e){
            return ResponseEntity.ok("Fehler : " + e.getMessage());

        }
    }
    @GetMapping("/start/{userId}")
    public ResponseEntity<String> getUserDutyStartTime(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getDutyStartTime(userId));
    }
    @GetMapping("/end/{userId}")
    public ResponseEntity<String> getUserDutyEndTime(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getDutyEndTime(userId));
    }
    @GetMapping("/duty/{userId}")
    public ResponseEntity<Boolean> isUserDuty(@PathVariable Long userId){
        return ResponseEntity.ok(userService.isUserDuty(userId));
    }
    @PostMapping("/start/{userId}/{dateTime}")
    public ResponseEntity<String> setUserDutyStartTime(@PathVariable Long userId, @PathVariable String dateTime){
        return ResponseEntity.ok(userService.setDutyStartTime(userId, dateTime));
    }
    @PostMapping("/end/{userId}/{dateTime}")
    public ResponseEntity<String> setUserDutyEndTime(@PathVariable Long userId, @PathVariable String dateTime){
        return ResponseEntity.ok(userService.setDutyEndTime(userId, dateTime));
    }
    @PostMapping("/duty/{userId}/{secondUserId}/{duty}")
    public ResponseEntity<String> setDuty(@PathVariable Long userId, @PathVariable Long secondUserId, @PathVariable Boolean duty){
        if(Objects.equals(userService.setDuty(userId, duty, secondUserId), "Acik bir tane Nöbet mevcut. Ilk onu kapatiniz")){
            return ResponseEntity.ok("Acik bir tane Nöbet mevcut. Ilk onu kapatiniz");
        }
        userService.setDuty(userId, duty, secondUserId);
        return ResponseEntity.ok("Degisti");
    }

    @PostMapping("/token/{pushToken}")
    public ResponseEntity<String> addPushToken(@PathVariable String pushToken, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        try {
            userRepository.updatePushTokenById(pushToken, userRepository.findByUsername(jwtTokenManager.getUsernameFromToken(jwtTokenManager.getUsernameFromToken(token.split(" ")[1]))).getId());
            return ResponseEntity.ok("Push Token eklendi");
        }catch (Exception e){
            return ResponseEntity.ok("Hata Olustu: + " + e.getMessage());
        }
    }
    @GetMapping("/personel")
    public ResponseEntity <List<User>> getAllPersonel(){
        return ResponseEntity.ok(userRepository.findByUserRole(UserRole.PERSONEL));
    }



}

package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.UserRole;
import com.farukgenc.boilerplate.springboot.service.RegistrationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class RegistrationCodeController {
    private final RegistrationCodeService registrationCodeService;


    @GetMapping("/{code}")
    public ResponseEntity<String> getByCode(@PathVariable Integer code){
        try {
            return ResponseEntity.ok(registrationCodeService.getByCode(code));
        }catch (Exception e){
            return ResponseEntity.ok("Kod hatalidir");
        }
    }

    @PostMapping("/{firstname}/{lastname}/{code}")
    public ResponseEntity<Object> createCode(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String code){
        UserRole userRole;
        userRole = UserRole.USER;
        if (code.toUpperCase().equals("PERSONEL")){
            userRole = UserRole.PERSONEL;
        }
       return ResponseEntity.ok(registrationCodeService.createCode(firstname, lastname, userRole ));
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> deleteCode(@PathVariable String username){
        return ResponseEntity.ok(registrationCodeService.deleteCode(username));
    }

    @PostMapping("/{code}/{password}/{mail}")
    public ResponseEntity<String> validateToken(@PathVariable Integer code, @PathVariable String mail, @PathVariable String password){
        return ResponseEntity.ok(registrationCodeService.validateCode(code,password,mail));
    }


}

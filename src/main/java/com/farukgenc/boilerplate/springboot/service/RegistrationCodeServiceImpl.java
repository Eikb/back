package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.RegistrationCode;
import com.farukgenc.boilerplate.springboot.model.UserRole;
import com.farukgenc.boilerplate.springboot.repository.RegistrationCodeRepository;
import com.farukgenc.boilerplate.springboot.repository.UserRepository;
import com.farukgenc.boilerplate.springboot.security.dto.RegistrationRequest;
import com.farukgenc.boilerplate.springboot.security.dto.RegistrationResponse;
import com.farukgenc.boilerplate.springboot.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationCodeServiceImpl implements RegistrationCodeService{
    private final UserRepository userRepository;

    private final RegistrationCodeRepository registrationCodeRepository;
    private final UserService userService;

    //Wenn der Code eingegeben wird, soll geprüft werden, ob der Code existiert wenn ja soll der Benutzername
    //ausgegeben werden
    @Override
    public String getByCode(Integer code) {
        try {
            return registrationCodeRepository.findByCode(code).getUsername();
        }catch (Exception e){
            return "Fehler: " + e.getMessage();
        }
    }

    //Hier wird der Code erzeugt Der Code ist eine Reheinfolge von 4 Zufälligen Zahlen
    @Override
    public Object createCode(String firstname, String lastname, UserRole userRole) {
        try {
            //Code erstellen
            Random random = new Random();
            int randomFourDigitNumber = random.nextInt((9999 - 1000) + 1) + 1000;
            int finalRandomFourDigitNumber = randomFourDigitNumber;
            while(registrationCodeRepository.findAll().stream().anyMatch(code -> code.getCode() == finalRandomFourDigitNumber)){
                randomFourDigitNumber = random.nextInt((9999 - 1000) + 1) + 1000;
            }

            //Benutzernamen erstellen
            String username = firstname + "." + lastname;

            //Registercode Objekt erstellen
            RegistrationCode registrationCode = new RegistrationCode();
            registrationCode.setName(firstname + " " + lastname);
            registrationCode.setUsername(username.toLowerCase());
            registrationCode.setCode(randomFourDigitNumber);
            registrationCode.setUserRole(userRole);

            registrationCodeRepository.save(registrationCode);

            return randomFourDigitNumber;
        } catch (Exception e){
            return "Hata: " + e.getMessage();
        }


    }

    @Override
    public String deleteCode(String username) {
        try{
            registrationCodeRepository.deleteByName(username);
            return "Silindi";
        } catch (Exception e){
            return "Silerken hata olustu: " + e.getMessage();
        }

    }

    @Override
    public String validateCode(Integer code, String password, String mail) {
try {
    RegistrationCode registrationCode = registrationCodeRepository.findByCode(code);
    RegistrationRequest registrationRequest = new RegistrationRequest();
    registrationRequest.setUsername(registrationCode.getUsername());
    registrationRequest.setName(registrationCode.getName());
    registrationRequest.setPassword(password);
    registrationRequest.setEmail(mail);
    final RegistrationResponse registrationResponse = userService.registration(registrationRequest);
    return registrationResponse.getMessage();
}
           catch (Exception e) {
                return "Fehler" + e.getMessage();
           }

    }
}

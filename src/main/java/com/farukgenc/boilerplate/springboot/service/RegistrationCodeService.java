package com.farukgenc.boilerplate.springboot.service;

public interface RegistrationCodeService {
    String getByCode(Integer code);
    Object createCode(String firstname, String lastname);
    String deleteCode(String username);
    String validateCode(Integer code, String password, String mail);
}

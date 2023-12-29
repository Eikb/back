package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.UserRole;

public interface RegistrationCodeService {
    String getByCode(Integer code);
    Object createCode(String firstname, String lastname, UserRole userRole);
    String deleteCode(String username);
    String validateCode(Integer code, String password, String mail);
}

package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.RegistrationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationCodeRepository extends JpaRepository<RegistrationCode,Long> {
    long deleteByCode(Integer code);
    long deleteByName(String name);
    RegistrationCode findByCode(Integer code);
}

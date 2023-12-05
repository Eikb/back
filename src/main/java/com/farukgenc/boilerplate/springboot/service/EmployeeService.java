package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Employee;
import com.farukgenc.boilerplate.springboot.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public String createEmployee(Employee employee){
        try{
            employeeRepository.save(employee);
            return "Personel kayit edilmistir";
        }catch (Exception e){
            return "Hata: " + e.getMessage();

        }
    }

    public Employee getEmployee(Long employeeId){
        try{
            return  employeeRepository.findById(employeeId).get();
        }catch (Exception e){
            return new Employee();
        }
    }

    public List<Employee> getAllEmployees(){
        try{
            return  employeeRepository.findAll();
        }catch (Exception e){
            return Collections.emptyList();
        }
    }

    public String deleteEmployeeById(Long employeeId){
        try{
            employeeRepository.deleteById(employeeId);
            return  "Silindi";
        }catch (Exception e){

            return "Personel bulunamadi: " + e.getMessage();
        }
    }

    public String updateEmployee(Long employeeId, Employee updatedEmployee){
        try{
            employeeRepository.updateNameAndPhoneNumberById(updatedEmployee.getName(),updatedEmployee.getPhoneNumber(), employeeId);
            return  "Degisiti";
        }catch (Exception e){

            return "Personel bulunamadi: " + e.getMessage();
        }
    }
}

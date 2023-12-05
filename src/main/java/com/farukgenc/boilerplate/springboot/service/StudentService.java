package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import com.farukgenc.boilerplate.springboot.model.enterExit.Student;
import com.farukgenc.boilerplate.springboot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public String createStudent(Student student) {
        try {
            studentRepository.save(student);
            return "Talebe kayit edilmisdir";
        }catch (Exception e) {
            return e.toString();
        }
    }

    public List<Student> getAllStudents(){
        try {
            return studentRepository.findAll();
        } catch (Exception e){
            return Collections.emptyList();
        }
    }

    public String deleteStudent(Long id){
        try{
            studentRepository.deleteById(id);
            return "Silindi";
        }
        catch (Exception e){
            return e.toString();
        }
    }

    public String editStudent(Long id, Student student) {
        studentRepository.updateNrAndNameAndDutyAndClassRoomById(student.getNr(),student.getName(),student.getDuty(),student.getClassRoom(), id);
        return "Updated";
    }

  public String saveListOfStudents(List<Student> studentList){
        try{
            studentRepository.saveAll(studentList);
            return "Saved list of Students. Length: " + studentList.size();
        }catch (Exception e){
            return e.getMessage();
        }
  }


}

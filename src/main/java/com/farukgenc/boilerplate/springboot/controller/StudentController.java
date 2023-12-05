package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.enterExit.Student;
import com.farukgenc.boilerplate.springboot.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@CrossOrigin
@RestController

@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @Operation(summary = "Get All Students", description = "Returns a list of all Students (Talebes)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Students not found - The product was not found")
    })
    @GetMapping
    public ResponseEntity<Object> getAllStudents(){
        List<Student> studentList = studentService.getAllStudents();
        try {
            studentList.sort(Comparator.comparingInt(Student::getNr));
            return ResponseEntity.ok(studentList);

        }catch (Exception e){
            return ResponseEntity.ok(studentList);

        }
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody Student student){
        try {
            student.setInBuilding(true);
            studentService.createStudent(student);
            return ResponseEntity.ok("Kayit Edilmisdir");
        }catch (Exception e){
            return ResponseEntity.ok("Hata Olusmustur: " + e.getMessage());
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId){
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok("Talebe silinmistir");
        }catch (Exception e){
            return ResponseEntity.ok("Bir hata olustu: " + e.getMessage());
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<String> updateStudent(@RequestBody Student student, @PathVariable Long studentId){
        try{
            studentService.editStudent(studentId,student);
            return ResponseEntity.ok("Talebe updatelendi");
        }catch (Exception e){
            return ResponseEntity.ok("Talebe bulunamadi : " + e.getMessage());
        }
    }

    @PostMapping("/collection")
    public ResponseEntity<String> saveListOfStudents(@RequestBody List<Student> studentList){
        return ResponseEntity.ok(studentService.saveListOfStudents(studentList));
    }
}

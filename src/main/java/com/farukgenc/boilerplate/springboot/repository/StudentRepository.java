package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.enterExit.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);
    @Transactional
    @Modifying
    @Query("update Student s set s.inBuilding = ?1 where s.id = ?2")
    int updateInBuildingById(Boolean inBuilding, Long id);
    @Transactional
    @Modifying
    @Query("update Student s set s.nr = ?1, s.name = ?2, s.duty = ?3, s.classRoom = ?4 where s.id = ?5")
    int updateNrAndNameAndDutyAndClassRoomById(Integer nr, String name, String duty, String classRoom, Long id);
}

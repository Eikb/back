package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.Duty;
import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface DutyRepository extends JpaRepository<Duty, Long> {
    @Transactional
    @Modifying
    @Query("update Duty d set d.completedTime = ?1, d.completed = ?2, d.active = ?3 where d.id = ?4")
    int updateCompletedTimeAndCompletedAndActiveById(LocalDateTime completedTime, Boolean completed, Boolean active, Long id);
    @Transactional
    @Modifying
    @Query("update Duty d set d.active = ?1 where d.id = ?2")
    int updateActiveById(Boolean active, Long id);
    Duty findByActive(Boolean active);
    @Transactional
    @Modifying
    @Query("update Duty d set d.completedTime = ?1 where d.id = ?2")
    int updateCompletedTimeById(LocalDateTime completedTime, Long id);

}

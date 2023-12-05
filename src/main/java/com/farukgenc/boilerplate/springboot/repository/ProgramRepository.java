package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.dailyprogram.DailyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<DailyProgram, Long> {
    @Transactional
    @Modifying
    @Query("update DailyProgram d set d.program = ?1, d.time = ?2 where d.id = ?3")
    int updateProgramAndTimeById(String program, String time, Long id);
    List<DailyProgram> findAllByOrderByTime();

}

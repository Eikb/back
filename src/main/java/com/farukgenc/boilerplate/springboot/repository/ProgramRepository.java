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
    @Query("update DailyProgram d set d.courseId = ?1, d.day = ?2, d.startTime = ?3, d.endTime = ?4, d.color = ?5 " +
            "where d.id = ?6")
    void updateCourseIdAndDayAndStartTimeAndEndTimeAndColorById(String courseId, Integer day, String startTime, String endTime, String color, Long id);

}

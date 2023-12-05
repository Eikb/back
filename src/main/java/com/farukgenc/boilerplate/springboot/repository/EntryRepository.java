package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    Entry findByStudentName(String studentName);
    @Transactional
    @Modifying
    @Query("update Entry e set e.enter = ?1 where e.id = ?2")
    int updateEnterById(LocalDateTime enter, Long id);

}

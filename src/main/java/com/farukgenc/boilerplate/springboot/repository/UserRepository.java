package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.Duty;
import com.farukgenc.boilerplate.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.dutyEndTime = ?1 where u.id = ?2")
    int updateDutyEndTimeById(LocalDateTime dutyEndTime, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.dutyStartTime = ?1 where u.id = ?2")
    int updateDutyStartTimeById(LocalDateTime dutyStartTime, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.duties = null where u.id = ?1")
    int updatea(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.duties = :updatedDuties WHERE u.id = :userId")
    void updateTasks(Long userId, List<Duty> updatedDuties);
    @Transactional
    @Modifying
    @Query("update User u set u.duties = ?1 where u.id = ?2")
    int updateDutiesById(List<Duty> duties, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.dutyEndTime = ?1 where u.id = ?2")
    void updateDutyEndTime(LocalDateTime dutyEndTime, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.dutyStartTime = ?1 where u.id = ?2")
    void updateDutyStartTime(LocalDateTime dutyStartTime, Long id);
    @Transactional
    @Modifying
    @Query("update User u set u.duty = ?1 where u.id = ?2")
    void updateDuty(Boolean duty, Long id);

	User findByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}

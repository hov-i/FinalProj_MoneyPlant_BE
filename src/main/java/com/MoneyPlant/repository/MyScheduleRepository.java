package com.MoneyPlant.repository;


import com.MoneyPlant.entity.MySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyScheduleRepository extends JpaRepository <MySchedule, Long> {

}

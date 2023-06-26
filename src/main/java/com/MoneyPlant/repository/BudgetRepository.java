package com.MoneyPlant.repository;

import com.MoneyPlant.dto.BudgetDto;
import com.MoneyPlant.entity.Budget;
import com.MoneyPlant.entity.Category;
import com.MoneyPlant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByUserAndCategory(User user, Category category);

    //@Query(value = "SELECT * FROM Budget WHERE user_id = :userId", nativeQuery = true)
    @Query("SELECT new com.MoneyPlant.dto.BudgetDto(b.user) " +
            "FROM Budget b " +
            "WHERE b.user = :userId")
    List<BudgetDto> findBudgetWithCategoryName(@Param("userId") Long userId);
}


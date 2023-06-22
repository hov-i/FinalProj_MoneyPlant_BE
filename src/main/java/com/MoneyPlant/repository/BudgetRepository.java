package com.MoneyPlant.repository;

import com.MoneyPlant.entity.Budget;
import com.MoneyPlant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

}

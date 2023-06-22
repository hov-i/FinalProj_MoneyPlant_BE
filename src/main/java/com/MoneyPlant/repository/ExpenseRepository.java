package com.MoneyPlant.repository;

import com.MoneyPlant.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // 아직 얘 역할이 뭐가 필요한지 잘 모르겠어요... 일단 넣어둠
}

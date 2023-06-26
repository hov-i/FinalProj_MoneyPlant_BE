package com.MoneyPlant.service;

import com.MoneyPlant.dto.BudgetDto;
import com.MoneyPlant.entity.Budget;
import com.MoneyPlant.entity.Category;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.BudgetRepository;
import com.MoneyPlant.repository.CategoryRepository;
import com.MoneyPlant.repository.UserRepository;
import com.MoneyPlant.service.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.security.auth.kerberos.KerberosKey;
import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // 나의 예산 생성
    public boolean createBudgetForCategories(BudgetDto budgetDto, UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        budgetDto.setUserId(userId);

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            Long categoryId = Long.valueOf(budgetDto.getCategoryId());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

            Budget budget = budgetRepository.findByUserAndCategory(user, category); // budget 조회

            if (budget == null) { // 만약 기존 budget 데이터가 없다면
                budget = new Budget();
                budget.setUser(user);
                budget.setCategory(category); // budget에 카테고리와 userId를 추가
            }

            budget.setBudgetMoney(budgetDto.getBudgetMoney()); // budget에 돈을 추가
            budget.setBudgetMonth(budgetDto.getBudgetMonth()); // budget에 달을 추가

            budgetRepository.save(budget); // budget 저장
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 나의 예산 조회
    public List<BudgetDto> getBudgetWithCategoryNames(UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();
        List<BudgetDto> budgetList = budgetRepository.findBudgetWithCategoryName(userId);
        return budgetList;
    }
}
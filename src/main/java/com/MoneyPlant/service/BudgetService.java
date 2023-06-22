package com.MoneyPlant.service;

import com.MoneyPlant.entity.Budget;
import com.MoneyPlant.entity.Category;
import com.MoneyPlant.entity.User;
import com.MoneyPlant.repository.BudgetRepository;
import com.MoneyPlant.repository.CategoryRepository;
import com.MoneyPlant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createBudgetForAllCategories(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found with id: " + userId));

        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            Budget budget = new Budget();
            budget.setUser(user);

            Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoryId());
            optionalCategory.ifPresent(budget::setCategory);

            budget.setBudgetMoney(0);
            budgetRepository.save(budget);
        }
    }
}

package com.MoneyPlant.repository;

import com.MoneyPlant.entity.CardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardList, Long> {
    void deleteAll();
    @Transactional
    void deleteByCardName(String cardName);

    @Query("SELECT c.cardName, COUNT(c.cardName) AS count FROM CardList c WHERE c.cardCategory IN :categories GROUP BY c.cardName HAVING COUNT(c.cardName) > 1")
    List<Object[]> findDuplicateCardNamesByCategories(@Param("categories") List<String> categories);
}

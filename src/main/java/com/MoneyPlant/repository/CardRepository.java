package com.MoneyPlant.repository;

import com.MoneyPlant.entity.CardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CardRepository extends JpaRepository<CardList, Long> {
    void deleteAll();
    @Transactional
    void deleteByCardName(String cardName);
}

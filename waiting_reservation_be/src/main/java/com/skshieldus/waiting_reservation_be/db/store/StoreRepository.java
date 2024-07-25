package com.skshieldus.waiting_reservation_be.db.store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findById(int id);
    List<StoreEntity> findAll();
}

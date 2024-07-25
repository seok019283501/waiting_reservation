package com.skshieldus.waiting_reservation_be.db.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findById(int id);
}

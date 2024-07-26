package com.skshieldus.waiting_reservation_be.db.store;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findById(int id);
    StoreEntity findByIdAndUsername(int id, String username);
    StoreEntity findByIdAndUsernameAndStatus(int id, String username, StoreStatus status);
    StoreEntity findByIdAndStatus(int id, StoreStatus status);
    List<StoreEntity> findAll();
    List<StoreEntity> findAllByStatus(StoreStatus status);
}

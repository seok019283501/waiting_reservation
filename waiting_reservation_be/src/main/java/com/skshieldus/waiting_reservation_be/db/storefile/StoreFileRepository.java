package com.skshieldus.waiting_reservation_be.db.storefile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreFileRepository extends JpaRepository<StoreFileEntity,Integer> {
    StoreFileEntity findByStoreId(int storeId);
}

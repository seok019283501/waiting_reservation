package com.skshieldus.waiting_reservation_be.db.store;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    StoreEntity findById(int id);
    StoreEntity findByIdAndUsername(int id, String username);
    StoreEntity findByIdAndUsernameAndStatus(int id, String username, StoreStatus status);
    StoreEntity findByIdAndStatus(int id, StoreStatus status);
    List<StoreEntity> findAll();
    List<StoreEntity> findAllByStatus(StoreStatus status);

    @Query(value = "SELECT s FROM StoreEntity s WHERE (s.storeName LIKE %:storeName% AND s.address LIKE %:address%) AND s.status= :status")
    List<StoreEntity> searchStoreNameAndAddress(@Param("storeName") String storeName, @Param("address") String  address, @Param("status") StoreStatus status);

    @Query(value = "SELECT s FROM StoreEntity s WHERE s.address LIKE %:address% AND s.status= :status")
    List<StoreEntity> searchAddress(@Param("address") String  address, @Param("status") StoreStatus status);

    @Query(value = "SELECT s FROM StoreEntity s WHERE s.storeName LIKE %:storeName% AND s.status= :status")
    List<StoreEntity> searchAllStoreName(@Param("storeName") String storeName, @Param("status") StoreStatus status);
}

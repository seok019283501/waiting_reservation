package com.skshieldus.waiting_reservation_be.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findAllByUsernameAndStoreId(String username,int storeId);
    List<OrderEntity> findAllByStoreId(int storeId);

    @Modifying
    @Transactional
    @Query("update OrderEntity m set m.deleteYn='Y' where m.username= :username")
    void deleteOrder(String username);
}

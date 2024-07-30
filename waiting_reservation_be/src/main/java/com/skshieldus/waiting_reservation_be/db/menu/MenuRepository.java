package com.skshieldus.waiting_reservation_be.db.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,Integer> {
    MenuEntity findById(int id);
    MenuEntity findByIdAndDeletedYn(int id, String deleteYn);
    List<MenuEntity> findAllByStoreId(int id);
    List<MenuEntity> findAllByStoreIdAndDeletedYn(int id,String deletedYn);

}

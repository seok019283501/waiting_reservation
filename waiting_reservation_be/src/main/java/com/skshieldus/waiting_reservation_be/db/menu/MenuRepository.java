package com.skshieldus.waiting_reservation_be.db.menu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity,Integer> {
    MenuEntity findById(int id);
}

package com.skshieldus.waiting_reservation_be.db.user.repository;

import com.skshieldus.waiting_reservation_be.db.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUsername(String username);
}

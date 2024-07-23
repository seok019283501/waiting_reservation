package com.skshieldus.waiting_reservation_be.domain.user.repository;

import com.skshieldus.waiting_reservation_be.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
}

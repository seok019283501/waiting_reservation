package com.skshieldus.waiting_reservation_be.db.user;

import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import com.skshieldus.waiting_reservation_be.db.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUsername(String username);
    UserEntity findByUsernameAndRole(String username, Role role);
}

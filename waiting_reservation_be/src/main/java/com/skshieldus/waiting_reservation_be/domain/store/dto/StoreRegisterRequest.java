package com.skshieldus.waiting_reservation_be.domain.store.dto;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreRegisterRequest {
    private String storeName;
    private String businessRegistrationUrl;
    private String username;
}

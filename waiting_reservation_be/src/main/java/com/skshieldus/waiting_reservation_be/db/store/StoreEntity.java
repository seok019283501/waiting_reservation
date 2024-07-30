package com.skshieldus.waiting_reservation_be.db.store;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;
import com.skshieldus.waiting_reservation_be.db.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
public class StoreEntity extends BaseEntity {
    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreStatus status;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime openAt;
    private String address;

}

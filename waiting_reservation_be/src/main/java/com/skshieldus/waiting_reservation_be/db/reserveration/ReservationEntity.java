package com.skshieldus.waiting_reservation_be.db.reserveration;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity extends BaseEntity {
    private String username;
    private int storeId;
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime insertAt;

}

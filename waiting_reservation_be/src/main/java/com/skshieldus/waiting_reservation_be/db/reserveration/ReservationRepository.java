package com.skshieldus.waiting_reservation_be.db.reserveration;

import com.skshieldus.waiting_reservation_be.db.reserveration.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Integer> {
    List<ReservationEntity> findAllByStoreIdAndStatus(int storeId, ReservationStatus status);
}

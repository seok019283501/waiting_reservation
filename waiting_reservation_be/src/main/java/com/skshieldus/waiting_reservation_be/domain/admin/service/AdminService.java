package com.skshieldus.waiting_reservation_be.domain.admin.service;

import com.skshieldus.waiting_reservation_be.db.store.enums.StoreStatus;

public interface AdminService {
    //식당 등록 허가
    void storeStatusChange(int id, StoreStatus status);
}

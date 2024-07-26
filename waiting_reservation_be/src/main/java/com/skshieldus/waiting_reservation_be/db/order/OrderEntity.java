package com.skshieldus.waiting_reservation_be.db.order;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_order")
public class OrderEntity extends BaseEntity {
    private int storeId;
    private String username;
    private int menuId;
}

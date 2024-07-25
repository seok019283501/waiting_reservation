package com.skshieldus.waiting_reservation_be.db.menu;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class MenuEntity extends BaseEntity {
    private int storeId;
    private String title;
    private String description;
    private int cost;
    private String deletedYn="N";

}

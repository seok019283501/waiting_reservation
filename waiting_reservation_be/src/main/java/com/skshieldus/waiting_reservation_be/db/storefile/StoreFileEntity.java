package com.skshieldus.waiting_reservation_be.db.storefile;

import com.skshieldus.waiting_reservation_be.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "store_file")
public class StoreFileEntity extends BaseEntity {

    @Column(nullable = false)
    private int storeId;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileSize;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private String username;

    private LocalDateTime updatedAt;


}

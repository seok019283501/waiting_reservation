package com.skshieldus.waiting_reservation_be.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreFileDto {
    private String filePath;
    private String originalFileName;
}

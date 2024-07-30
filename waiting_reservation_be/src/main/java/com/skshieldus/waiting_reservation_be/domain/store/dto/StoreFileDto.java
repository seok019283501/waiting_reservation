package com.skshieldus.waiting_reservation_be.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StoreFileDto {
    private int idx;
    private int boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;		// 천단위 콤마를 쿼리에서 적용한 결과를 저장해야 하므로
    private String createdDatetime;
    private String creatorId;
    private String updatedDatetime;
    private String updaterId;
}

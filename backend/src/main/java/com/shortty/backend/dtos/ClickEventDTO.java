package com.shortty.backend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClickEventDTO {
    private LocalDate clickedAt;
    private Long count;
}

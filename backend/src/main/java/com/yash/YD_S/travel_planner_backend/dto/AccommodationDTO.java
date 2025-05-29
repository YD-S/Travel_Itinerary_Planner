package com.yash.YD_S.travel_planner_backend.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String name;
    private String address;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String bookingReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

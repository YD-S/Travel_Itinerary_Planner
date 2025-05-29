package com.yash.YD_S.travel_planner_backend.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDTO {
    private Long id;
    private String name;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<AccommodationDTO> accommodations;
}

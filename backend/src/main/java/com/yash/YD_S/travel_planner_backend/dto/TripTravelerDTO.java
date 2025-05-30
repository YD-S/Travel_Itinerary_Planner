package com.yash.YD_S.travel_planner_backend.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripTravelerDTO {
    private Long tripId;
    private Long travelerId;
    private String role;
    private String notes;
    private String travelerType;
    private UserDTO traveler;
}

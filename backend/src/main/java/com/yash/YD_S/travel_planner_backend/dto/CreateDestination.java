package com.yash.YD_S.travel_planner_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDestination {
    private String name;
    private String arrivalDate;
    private String departureDate;
}
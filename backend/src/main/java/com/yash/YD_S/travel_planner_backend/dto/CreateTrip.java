package com.yash.YD_S.travel_planner_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateTrip {
    private String title;
    private String description;
    private List<CreateDestination> destinations;
    private String startDate;
    private String endDate;
}

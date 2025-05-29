package com.yash.YD_S.travel_planner_backend.dto;

import com.yash.YD_S.travel_planner_backend.model.Tasks;
import com.yash.YD_S.travel_planner_backend.model.TripTraveler;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateTrip {
    private String title;
    private String description;
    private List<CreateDestination> destinations;
    private String startDate;
    private String endDate;
    private List<Tasks> tasks;
    private List<TripTraveler> travelers;
}

package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.TripDTO;
import com.yash.YD_S.travel_planner_backend.model.Trip;

import java.util.Collections;

public class TripMapper {

    public static TripDTO toTripDTO(Trip trip) {
        return TripDTO.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .description(trip.getDescription())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .createdAt(trip.getCreatedAt())
                .updatedAt(trip.getUpdatedAt())
                .activities(trip.getActivities() != null
                        ? trip.getActivities().stream()
                        .map(ActivityMapper::toDTO)
                        .toList()
                        : Collections.emptyList())
                .destinations(trip.getDestinations() != null
                        ? trip.getDestinations().stream()
                        .map(DestinationMapper::toDTO)
                        .toList()
                        : Collections.emptyList())
                .travelers(trip.getTripTravelers() != null
                        ? trip.getTripTravelers().stream()
                        .map(TripTravelerMapper::toDTO)
                        .toList()
                        : Collections.emptyList())
                .tasks(trip.getTasks() != null
                        ? trip.getTasks().stream()
                        .map(TaskMapper::toDTO)
                        .toList()
                        : Collections.emptyList())
                .build();
    }
}

package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.DestinationDTO;
import com.yash.YD_S.travel_planner_backend.model.Destination;

public class DestinationMapper {
    public static DestinationDTO toDTO(Destination destination) {
        return DestinationDTO.builder()
                .id(destination.getId())
                .name(destination.getName())
                .arrivalDate(destination.getArrivalDate())
                .departureDate(destination.getDepartureDate())
                .createdAt(destination.getCreatedAt())
                .updatedAt(destination.getUpdatedAt())
                .build();
    }
}

package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.DestinationDTO;
import com.yash.YD_S.travel_planner_backend.model.Destination;

import java.util.stream.Collectors;

public class DestinationMapper {
    public static DestinationDTO toDTO(Destination destination) {
        return DestinationDTO.builder()
                .id(destination.getId())
                .name(destination.getName())
                .arrivalDate(destination.getArrivalDate())
                .departureDate(destination.getDepartureDate())
                .createdAt(destination.getCreatedAt())
                .updatedAt(destination.getUpdatedAt())
                .accommodations(destination.getAccommodations() != null
                        ? destination.getAccommodations().stream()
                        .map(AccomodationMapper::toDTO)
                        .toList()
                        : null)
                .build();
    }

    public static Destination toEntity(DestinationDTO destinationDTO) {
        return Destination.builder()
                .id(destinationDTO.getId())
                .name(destinationDTO.getName())
                .arrivalDate(destinationDTO.getArrivalDate())
                .departureDate(destinationDTO.getDepartureDate())
                .createdAt(destinationDTO.getCreatedAt())
                .updatedAt(destinationDTO.getUpdatedAt())
                .accommodations(destinationDTO.getAccommodations() != null
                        ? destinationDTO.getAccommodations().stream()
                        .map(AccomodationMapper::toEntity)
                        .collect(Collectors.toSet())
                        : null)
                .build();
    }
}

package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.AccommodationDTO;
import com.yash.YD_S.travel_planner_backend.model.Accommodation;

public class AccomodationMapper {
    public static AccommodationDTO toDTO(Accommodation accommodation) {
        return AccommodationDTO.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .address(accommodation.getAddress())
                .checkIn(accommodation.getCheckIn())
                .checkOut(accommodation.getCheckOut())
                .createdAt(accommodation.getCreatedAt())
                .updatedAt(accommodation.getUpdatedAt())
                .build();
    }

    public static Accommodation toEntity(AccommodationDTO accommodationDTO) {
        return Accommodation.builder()
                .id(accommodationDTO.getId())
                .name(accommodationDTO.getName())
                .address(accommodationDTO.getAddress())
                .checkIn(accommodationDTO.getCheckIn())
                .checkOut(accommodationDTO.getCheckOut())
                .createdAt(accommodationDTO.getCreatedAt())
                .updatedAt(accommodationDTO.getUpdatedAt())
                .build();
    }
}

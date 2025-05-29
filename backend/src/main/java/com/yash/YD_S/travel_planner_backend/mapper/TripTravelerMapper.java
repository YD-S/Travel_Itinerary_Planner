package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.TripTravelerDTO;
import com.yash.YD_S.travel_planner_backend.model.TripTraveler;

public class TripTravelerMapper {
    public static TripTravelerDTO toDTO(TripTraveler traveler) {
        return TripTravelerDTO.builder()
                .tripId(traveler.getTrip().getId())
                .travelerId(traveler.getTraveler().getId())
                .role(traveler.getRole())
                .notes(traveler.getNotes())
                .travelerType(String.valueOf(traveler.getTravelerType()))
                .build();
    }
}

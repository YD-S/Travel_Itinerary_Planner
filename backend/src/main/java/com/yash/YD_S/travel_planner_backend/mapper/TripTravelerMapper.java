package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.TripTravelerDTO;
import com.yash.YD_S.travel_planner_backend.model.*;
import com.yash.YD_S.travel_planner_backend.repository.TripRepository;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;

public class TripTravelerMapper {

    public static TripTravelerDTO toDTO(TripTraveler traveler) {
        return TripTravelerDTO.builder()
                .tripId(traveler.getTrip().getId())
                .travelerId(traveler.getTraveler().getId())
                .role(traveler.getRole())
                .notes(traveler.getNotes())
                .travelerType(traveler.getTravelerType() != null
                        ? traveler.getTravelerType().name()
                        : null)
                .traveler(UserMapper.toDTO(traveler.getTraveler()))
                .build();
    }

    public static TripTraveler toEntity(TripTravelerDTO dto, TripRepository tripRepository, UserRepository userRepository) {
        Trip trip = tripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + dto.getTripId()));

        User traveler = userRepository.findById(dto.getTravelerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getTravelerId()));

        return TripTraveler.builder()
                .id(new TripTravelerId(trip.getId(), traveler.getId()))
                .trip(trip)
                .traveler(traveler)
                .role(dto.getRole())
                .notes(dto.getNotes())
                .travelerType(dto.getTravelerType() != null
                        ? TravelerType.valueOf(dto.getTravelerType())
                        : null)
                .build();
    }
}

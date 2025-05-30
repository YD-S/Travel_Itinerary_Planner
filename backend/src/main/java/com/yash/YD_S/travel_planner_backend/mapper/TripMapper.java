package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.TripDTO;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.repository.TripRepository;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class TripMapper {
    private static TripRepository tripRepository;
    private static UserRepository userRepository;

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

    public static Trip toEntity(TripDTO tripDTO) {
        return Trip.builder()
                .id(tripDTO.getId())
                .title(tripDTO.getTitle())
                .description(tripDTO.getDescription())
                .startDate(tripDTO.getStartDate())
                .endDate(tripDTO.getEndDate())
                .createdAt(tripDTO.getCreatedAt())
                .updatedAt(tripDTO.getUpdatedAt())
                .user(tripDTO.getUser() != null ? UserMapper.toEntity(tripDTO.getUser()) : null)
                .activities(tripDTO.getActivities() != null
                        ? tripDTO.getActivities().stream()
                        .map(ActivityMapper::toEntity)
                        .collect(Collectors.toSet())
                        : Collections.emptySet())
                .destinations(tripDTO.getDestinations() != null
                        ? tripDTO.getDestinations().stream()
                        .map(DestinationMapper::toEntity)
                        .collect(Collectors.toSet())
                        : Collections.emptySet())
                .tasks(tripDTO.getTasks() != null
                        ? tripDTO.getTasks().stream()
                        .map(TaskMapper::toEntity)
                        .collect(Collectors.toSet())
                        : Collections.emptySet())
                .tripTravelers(tripDTO.getTravelers() != null
                        ? tripDTO.getTravelers().stream()
                        .map(dto -> TripTravelerMapper.toEntity(dto, tripRepository, userRepository))
                        .collect(Collectors.toSet())
                        : Collections.emptySet())
                .build();
    }

    public static void setTripRepository(TripRepository tripRepository) {
        TripMapper.tripRepository = tripRepository;
    }

    public static void setUserRepository(UserRepository userRepository) {
        TripMapper.userRepository = userRepository;
    }
}

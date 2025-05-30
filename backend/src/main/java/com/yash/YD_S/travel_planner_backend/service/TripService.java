package com.yash.YD_S.travel_planner_backend.service;

import com.yash.YD_S.travel_planner_backend.dto.*;
import com.yash.YD_S.travel_planner_backend.mapper.*;
import com.yash.YD_S.travel_planner_backend.model.Destination;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.TripTraveler;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.DestinationRepository;
import com.yash.YD_S.travel_planner_backend.repository.TripRepository;
import com.yash.YD_S.travel_planner_backend.repository.TripTravelerRepository;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final DestinationRepository destinationRepository;
    private final TripTravelerRepository tripTravelerRepository;
    private final UserRepository userRepository;

    private Set<Destination> setDestinations(TripDTO tripData, Trip newTrip) {
        Set<Destination> newDestinations = new HashSet<>();
        for (DestinationDTO destData : tripData.getDestinations()) {
            Destination destination = Destination.builder()
                    .name(destData.getName())
                    .arrivalDate(destData.getArrivalDate())
                    .departureDate(destData.getDepartureDate())
                    .trip(newTrip)
                    .build();
            newDestinations.add(destination);
        }

        newTrip.setDestinations(newDestinations);
        return newDestinations;
    }


    private boolean canUserEditTrip(Trip trip, User user) {
        if (trip.getUser().equals(user)) {
            return true;
        }

        return tripTravelerRepository.findAllByTripId(trip.getId()).stream()
                .anyMatch(tt -> tt.getTraveler().equals(user));
    }

    private void updateTripDetails(Trip trip, TripDTO tripData) {
        if (tripData.getTitle() != null) {
            trip.setTitle(tripData.getTitle());
        }
        if (tripData.getDescription() != null) {
            trip.setDescription(tripData.getDescription());
        }
        if (tripData.getStartDate() != null) {
            trip.setStartDate(tripData.getStartDate());
        }
        if (tripData.getEndDate() != null) {
            trip.setEndDate(tripData.getEndDate());
        }
    }

    private void updateDestinations(Trip trip, TripDTO tripData) {
        Set<Destination> updatedDestinations = setDestinations(tripData, trip);
        if (!updatedDestinations.isEmpty()) {
            Set<Destination> existingDestinations = trip.getDestinations();
            existingDestinations.addAll(updatedDestinations);
            trip.setDestinations(existingDestinations);
            destinationRepository.saveAll(updatedDestinations);
        }
    }

    private void updateTravelers(Trip trip, TripDTO tripData) {
        if (tripData.getTravelers() == null || tripData.getTravelers().isEmpty()) return;

        Set<User> existingTravelers = tripTravelerRepository.findAllByTripId(trip.getId()).stream()
                .map(TripTraveler::getTraveler)
                .collect(Collectors.toSet());

        for (TripTravelerDTO incoming : tripData.getTravelers()) {
            UserDTO user = incoming.getTraveler();
            if (user == null || user.getId() == null) {
                throw new RuntimeException("Traveler user is not valid");
            }
            User userEntity = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));
            if (!existingTravelers.contains(userEntity)) {
                tripTravelerRepository.save(new TripTraveler(trip, userEntity));
            }
        }
    }


    public Trip createTrip(TripDTO tripDTO, User user) {
        Trip trip = Trip.builder()
                .id(tripDTO.getId())
                .title(tripDTO.getTitle())
                .description(tripDTO.getDescription())
                .startDate(tripDTO.getStartDate())
                .endDate(tripDTO.getEndDate())
                .createdAt(tripDTO.getCreatedAt())
                .updatedAt(tripDTO.getUpdatedAt())
                .user(user)
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

        Set<Destination> destinations =  setDestinations(tripDTO, trip);
        Trip savedTrip = tripRepository.save(trip);
        destinationRepository.saveAll(destinations);

        return savedTrip;
    }

    public void deleteTrip(User user, Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
        if (!trip.getUser().equals(user)) {
            throw new RuntimeException("You do not have permission to delete this trip" + tripId);
        }
        destinationRepository.deleteAll(trip.getDestinations());
        tripRepository.delete(trip);
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with ID: " + tripId));
    }

    public Trip updateTrip(Long tripId, TripDTO tripData, User user) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new AccessDeniedException("Trip not found with ID: " + tripId));

        if (!canUserEditTrip(existingTrip, user)) {
            throw new AccessDeniedException("You do not have permission to update this trip: " + tripId);
        }
        updateTripDetails(existingTrip, tripData);
        updateTravelers(existingTrip, tripData);
        updateDestinations(existingTrip, tripData);
        return tripRepository.save(existingTrip);
    }

}
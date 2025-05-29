package com.yash.YD_S.travel_planner_backend.service;

import com.yash.YD_S.travel_planner_backend.dto.CreateDestination;
import com.yash.YD_S.travel_planner_backend.dto.CreateTrip;
import com.yash.YD_S.travel_planner_backend.dto.UpdateTrip;
import com.yash.YD_S.travel_planner_backend.model.Destination;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.DestinationRepository;
import com.yash.YD_S.travel_planner_backend.repository.TripRepository;
import com.yash.YD_S.travel_planner_backend.repository.TripTravelerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final DestinationRepository destinationRepository;
    private final TripTravelerRepository tripTravelerRepository;

    private Set<Destination> SetDestinationsForUpdate(UpdateTrip tripData, Trip existingTrip) {
        Set<Destination> updatedDestinations = new HashSet<>();
        for (CreateDestination destData : tripData.getDestinations()) {
            Destination destination = Destination.builder()
                    .name(destData.getName())
                    .arrivalDate(LocalDate.parse(destData.getArrivalDate()))
                    .departureDate(LocalDate.parse(destData.getDepartureDate()))
                    .trip(existingTrip)
                    .build();
            updatedDestinations.add(destination);
        }

        existingTrip.setDestinations(updatedDestinations);
        return updatedDestinations;
    }

    private Set<Destination> setDestinationsFromCreate(CreateTrip tripData, Trip newTrip) {
        Set<Destination> newDestinations = new HashSet<>();
        for (CreateDestination destData : tripData.getDestinations()) {
            Destination destination = Destination.builder()
                    .name(destData.getName())
                    .arrivalDate(LocalDate.parse(destData.getArrivalDate()))
                    .departureDate(LocalDate.parse(destData.getDepartureDate()))
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

    private void updateTripDetails(Trip trip, UpdateTrip tripData) {
        if (tripData.getTitle() != null) {
            trip.setTitle(tripData.getTitle());
        }
        if (tripData.getDescription() != null) {
            trip.setDescription(tripData.getDescription());
        }
        if (tripData.getStartDate() != null) {
            trip.setStartDate(LocalDate.parse(tripData.getStartDate()));
        }
        if (tripData.getEndDate() != null) {
            trip.setEndDate(LocalDate.parse(tripData.getEndDate()));
        }
    }

    private void updateDestinations(Trip trip, UpdateTrip tripData) {
        Set<Destination> updatedDestinations = SetDestinationsForUpdate(tripData, trip);
        destinationRepository.saveAll(updatedDestinations);
    }


    public Trip createTrip(CreateTrip tripData, User user) {
        Trip trip = Trip.builder()
                .title(tripData.getTitle())
                .description(tripData.getDescription())
                .startDate(LocalDate.parse(tripData.getStartDate()))
                .endDate(LocalDate.parse(tripData.getEndDate()))
                .user(user)
                .build();

        Set<Destination> destinations =  setDestinationsFromCreate(tripData, trip);
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

    public Trip updateTrip(Long tripId, UpdateTrip tripData, User user) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new AccessDeniedException("Trip not found with ID: " + tripId));

        if (!canUserEditTrip(existingTrip, user)) {
            throw new AccessDeniedException("You do not have permission to update this trip: " + tripId);
        }
        updateTripDetails(existingTrip, tripData);
        updateDestinations(existingTrip, tripData);
        return tripRepository.save(existingTrip);
    }

}
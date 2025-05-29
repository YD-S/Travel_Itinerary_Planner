package com.yash.YD_S.travel_planner_backend.service;

import com.yash.YD_S.travel_planner_backend.dto.CreateDestination;
import com.yash.YD_S.travel_planner_backend.dto.CreateTrip;
import com.yash.YD_S.travel_planner_backend.model.Destination;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.DestinationRepository;
import com.yash.YD_S.travel_planner_backend.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final DestinationRepository destinationRepository;

    public Trip createTrip(CreateTrip tripData, User user) {
        Trip trip = Trip.builder()
                .title(tripData.getTitle())
                .description(tripData.getDescription())
                .startDate(LocalDate.parse(tripData.getStartDate()))
                .endDate(LocalDate.parse(tripData.getEndDate()))
                .user(user)
                .build();

        Set<Destination> destinations = new HashSet<>();
        for (CreateDestination destData : tripData.getDestinations()) {
            Destination destination = Destination.builder()
                    .name(destData.getName())
                    .arrivalDate(LocalDate.parse(destData.getArrivalDate()))
                    .departureDate(LocalDate.parse(destData.getDepartureDate()))
                    .trip(trip)
                    .build();
            destinations.add(destination);
        }

        trip.setDestinations(destinations);
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
}
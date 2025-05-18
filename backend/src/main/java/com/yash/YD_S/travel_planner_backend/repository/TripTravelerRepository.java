package com.yash.YD_S.travel_planner_backend.repository;

import com.yash.YD_S.travel_planner_backend.model.Traveler;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.TripTraveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripTravelerRepository extends JpaRepository<TripTraveler, Long> {

    List<TripTraveler> findByTraveler(Traveler traveler);
    List<TripTraveler> findByTravelerId(Long travelerId);
    List<TripTraveler> findByTrip(Trip trip);
    List<TripTraveler> findByTripId(Long tripId);
}

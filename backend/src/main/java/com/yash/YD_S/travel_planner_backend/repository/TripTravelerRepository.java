package com.yash.YD_S.travel_planner_backend.repository;

import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.TripTraveler;
import com.yash.YD_S.travel_planner_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripTravelerRepository extends JpaRepository<TripTraveler, Long> {
    List<TripTraveler> findByTraveler(User traveler);
    List<TripTraveler> findByTravelerId(Long travelerId);
    List<TripTraveler> findByTrip(Trip trip);
    List<TripTraveler> findAllByTripId(Long tripId);
}

package com.yash.YD_S.travel_planner_backend.repository;

import com.yash.YD_S.travel_planner_backend.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findById(Long tripId);
    boolean existsById(Long tripId);
    Optional<Trip> findByTitle(String tripName);
    boolean existsByTitle(String tripName);
    List<Trip> findAllByUserId(Long userId);
}

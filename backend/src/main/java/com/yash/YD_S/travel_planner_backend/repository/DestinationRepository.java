package com.yash.YD_S.travel_planner_backend.repository;

import com.yash.YD_S.travel_planner_backend.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    Optional<Destination> findByName(String destinationName);
    List<Destination> findAllByTripId(Long tripId);
}

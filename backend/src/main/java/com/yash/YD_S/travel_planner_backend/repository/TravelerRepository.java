package com.yash.YD_S.travel_planner_backend.repository;

import com.yash.YD_S.travel_planner_backend.model.Traveler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    Optional<Traveler> findByName(Traveler traveler);
}

package com.yash.YD_S.travel_planner_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
public class TripTravelerId implements Serializable {

    @Column(name = "trip_id")
    private Long tripId;

    @Column(name = "traveler_id")
    private Long travelerId;
}
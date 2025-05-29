package com.yash.YD_S.travel_planner_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip_traveler")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TripTraveler {
    @EmbeddedId
    private TripTravelerId id = new TripTravelerId();

    @ManyToOne
    @MapsId("tripId")
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @MapsId("travelerId")
    @JoinColumn(name = "traveler_id")
    private User traveler;

    @Column(name = "role")
    private String role; // Optional (e.g. 'primary', 'guest')

    @Column(name = "notes")
    private String notes;
}

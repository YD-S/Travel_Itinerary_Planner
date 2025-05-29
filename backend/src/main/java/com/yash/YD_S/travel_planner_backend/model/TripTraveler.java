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
    private String role;

    @Column(name = "notes")
    private String notes;

    @Column(name = "traveler_type")
    @Enumerated(EnumType.STRING)
    private TravelerType travelerType;

    public TripTraveler(Trip trip, User user) {
        this.trip = trip;
        this.traveler = user;
        this.id = new TripTravelerId(trip.getId(), user.getId());
        this.role = "guest";
        this.notes = "";
        this.travelerType = null;
    }
}

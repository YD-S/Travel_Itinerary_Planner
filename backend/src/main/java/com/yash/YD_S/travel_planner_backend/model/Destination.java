package com.yash.YD_S.travel_planner_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "destination")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Accommodation> accommodations = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
        accommodation.setDestination(this);
    }

    public void removeAccommodation(Accommodation accommodation) {
        accommodations.remove(accommodation);
        accommodation.setDestination(null);
    }
}

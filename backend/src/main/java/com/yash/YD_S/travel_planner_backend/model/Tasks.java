package com.yash.YD_S.travel_planner_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "taskName", nullable = false)
    private String taskName;

    @Column(name = "taskDescription", nullable = false)
    private String taskDescription;

    @Column(name = "completed", columnDefinition="boolean default false")
    private boolean completed;
}

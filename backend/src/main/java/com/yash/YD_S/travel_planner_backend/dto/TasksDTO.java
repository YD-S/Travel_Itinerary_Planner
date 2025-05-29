package com.yash.YD_S.travel_planner_backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TasksDTO {
    private Long id;
    private String taskName;
    private String taskDescription;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

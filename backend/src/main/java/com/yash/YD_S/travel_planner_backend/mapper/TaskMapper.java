package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.TasksDTO;
import com.yash.YD_S.travel_planner_backend.model.Tasks;

public class TaskMapper {
    public static TasksDTO toDTO(Tasks task) {
        return TasksDTO.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .taskDescription(task.getTaskDescription())
                .completed(task.isCompleted())
                .build();
    }

    public static Tasks toEntity(TasksDTO tasksDTO) {
        return Tasks.builder()
                .id(tasksDTO.getId())
                .taskName(tasksDTO.getTaskName())
                .taskDescription(tasksDTO.getTaskDescription())
                .completed(tasksDTO.isCompleted())
                .build();
    }
}

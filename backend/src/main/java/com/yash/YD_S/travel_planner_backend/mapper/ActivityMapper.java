package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.ActivityDTO;
import com.yash.YD_S.travel_planner_backend.model.Activity;

import java.math.BigDecimal;

public class ActivityMapper {
    public static ActivityDTO toDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .location(activity.getLocation())
                .cost(activity.getCost() != null ? activity.getCost() : BigDecimal.ZERO)
                .build();
    }

    public static Activity toEntity(ActivityDTO activityDTO) {
        return Activity.builder()
                .id(activityDTO.getId())
                .name(activityDTO.getName())
                .description(activityDTO.getDescription())
                .startTime(activityDTO.getStartTime())
                .endTime(activityDTO.getEndTime())
                .location(activityDTO.getLocation())
                .cost(activityDTO.getCost() != null ? activityDTO.getCost() : BigDecimal.ZERO)
                .build();
    }
}

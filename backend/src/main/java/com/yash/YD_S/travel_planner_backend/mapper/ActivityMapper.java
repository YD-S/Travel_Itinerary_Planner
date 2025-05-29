package com.yash.YD_S.travel_planner_backend.mapper;

import com.yash.YD_S.travel_planner_backend.dto.ActivityDTO;
import com.yash.YD_S.travel_planner_backend.model.Activity;

public class ActivityMapper {
    public static ActivityDTO toDTO(Activity activity) {
        return ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .location(activity.getLocation())
                .build();
    }
}

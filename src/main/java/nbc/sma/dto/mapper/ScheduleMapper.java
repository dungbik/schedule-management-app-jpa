package nbc.sma.dto.mapper;

import nbc.sma.dto.request.ScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.SchedulesResponse;
import nbc.sma.entity.Schedule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleMapper {

    public Schedule toEntity(ScheduleRequest req) {
        return Schedule.builder()
                .userName(req.userName())
                .title(req.title())
                .task(req.task())
                .build();
    }

    public ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getUserName(),
                schedule.getTitle(),
                schedule.getTask(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    public SchedulesResponse toResponse(List<Schedule> schedules) {
        return new SchedulesResponse(
                schedules.stream().map(this::toResponse).toList()
        );
    }
}

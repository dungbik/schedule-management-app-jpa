package nbc.sma.dto.mapper;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.ScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.SchedulesResponse;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleMapper {

    private final UserMapper userMapper;

    public Schedule toEntity(ScheduleRequest req, User user) {
        return Schedule.builder()
                .user(user)
                .title(req.title())
                .task(req.task())
                .build();
    }

    public ScheduleResponse toResponse(Schedule schedule) {
        UserResponse userResponse = userMapper.toResponse(schedule.getUser());

        return new ScheduleResponse(
                schedule.getId(),
                userResponse,
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

package nbc.sma.dto.mapper;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.CreateScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ScheduleMapper {

    private final UserMapper userMapper;

    public Schedule toEntity(CreateScheduleRequest req, User user) {
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
}

package nbc.sma.dto.response;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        UserResponse user,
        String title,
        String task,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

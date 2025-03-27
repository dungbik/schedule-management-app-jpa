package nbc.sma.dto.request;

public record ScheduleRequest(
        Long userId,
        String title,
        String task
) {
}

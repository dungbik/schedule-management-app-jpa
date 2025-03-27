package nbc.sma.dto.request;

public record UpdateScheduleRequest(
        String userName,
        String title,
        String task
) {
}

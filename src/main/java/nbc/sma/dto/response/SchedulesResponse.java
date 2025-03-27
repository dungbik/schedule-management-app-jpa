package nbc.sma.dto.response;

import java.util.List;

public record SchedulesResponse(
        List<ScheduleResponse> results
) {
}

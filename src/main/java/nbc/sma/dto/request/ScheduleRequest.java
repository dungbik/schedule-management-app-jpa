package nbc.sma.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ScheduleRequest(
        @NotNull @Length(max = 50)  String title,
        @NotNull @Length(max = 200) String task
) {
}

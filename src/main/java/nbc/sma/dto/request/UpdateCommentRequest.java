package nbc.sma.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateCommentRequest(
        @NotNull @Length(max = 200) String content
) {
}

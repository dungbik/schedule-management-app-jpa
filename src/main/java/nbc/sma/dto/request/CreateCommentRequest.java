package nbc.sma.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateCommentRequest(
        @NotNull @Length(max = 200) String content
) {
}

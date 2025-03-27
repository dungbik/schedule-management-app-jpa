package nbc.sma.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequest(
        @NotNull @Length(max = 30)        String name,

        @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
        @NotNull @Length(max = 30) String email
) {
}

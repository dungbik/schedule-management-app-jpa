package nbc.sma.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Pattern(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
        @NotNull @Length(max = 30) String email,

        @NotNull @Length(max = 30)        String password
) {
}

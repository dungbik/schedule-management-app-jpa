package nbc.sma.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        @NotNull @Length(max = 30)        String name,
        @NotNull @Email @Length(max = 30) String email,
        @NotNull @Length(max = 30)        String password
) {
}

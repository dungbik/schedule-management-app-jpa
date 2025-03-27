package nbc.sma.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequest(
        @NotNull @Length(max = 30)        String name,
        @NotNull @Email @Length(max = 30) String email
) {
}

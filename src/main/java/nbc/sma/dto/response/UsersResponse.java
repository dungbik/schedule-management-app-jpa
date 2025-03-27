package nbc.sma.dto.response;

import java.util.List;

public record UsersResponse(
        List<UserResponse> results
) {
}

package nbc.sma.dto.request;

public record SignUpRequest(
        String name,
        String email,
        String password
) {
}

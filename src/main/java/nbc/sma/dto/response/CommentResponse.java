package nbc.sma.dto.response;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        UserResponse user,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

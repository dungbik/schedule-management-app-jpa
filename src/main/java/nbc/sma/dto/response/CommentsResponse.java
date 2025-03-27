package nbc.sma.dto.response;

import java.util.List;

public record CommentsResponse(
        List<CommentResponse> results
) {
}

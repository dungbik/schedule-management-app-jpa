package nbc.sma.dto.mapper;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.response.CommentResponse;
import nbc.sma.dto.response.CommentsResponse;
import nbc.sma.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                userMapper.toResponse(comment.getUser()),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public CommentsResponse toResponse(List<Comment> comments) {
        return new CommentsResponse(
                comments.stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

}

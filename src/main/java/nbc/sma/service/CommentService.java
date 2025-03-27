package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.mapper.CommentMapper;
import nbc.sma.dto.request.CreateCommentRequest;
import nbc.sma.dto.request.UpdateCommentRequest;
import nbc.sma.dto.response.CommentResponse;
import nbc.sma.entity.Comment;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import nbc.sma.exception.ForbiddenException;
import nbc.sma.exception.NotFoundException;
import nbc.sma.repository.CommentRepository;
import nbc.sma.repository.ScheduleRepository;
import nbc.sma.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentMapper commentMapper;

    public CommentResponse createComment(Long scheduleId, Long userId, CreateCommentRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        Comment comment = Comment.builder()
                .content(req.content())
                .schedule(schedule)
                .user(user)
                .build();
        commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    public Page<CommentResponse> findComments(Long scheduleId, Pageable pageable) {
        return commentRepository.findAllBySchedule(scheduleId, pageable);
    }

    public void updateComment(Long scheduleId, Long commentId, Long userId, UpdateCommentRequest req) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        validateComment(schedule, comment, userId);

        comment.updateContent(req.content());
        commentRepository.save(comment);
    }

    public void deleteComment(Long scheduleId, Long commentId, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        validateComment(schedule, comment, userId);

        commentRepository.delete(comment);
    }

    private void validateComment(Schedule schedule, Comment comment, Long userId) {
        boolean isScheduleIdMismatch = !Objects.equals(schedule.getId(), comment.getSchedule().getId());
        if (isScheduleIdMismatch) {
            throw new NotFoundException("Schedule id mismatch");
        }

        boolean isNotOwner = !Objects.equals(userId, comment.getUser().getId());
        if (isNotOwner) {
            throw new ForbiddenException("You are not the owner of this Comment.");
        }
    }
}

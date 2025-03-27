package nbc.sma.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.CreateCommentRequest;
import nbc.sma.dto.request.UpdateCommentRequest;
import nbc.sma.dto.response.CommentResponse;
import nbc.sma.security.SessionConst;
import nbc.sma.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest req,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        CommentResponse res = commentService.createComment(scheduleId, userId, req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponse>> findComments(
            @PathVariable Long scheduleId,
            @PageableDefault Pageable pageable
    ) {
        Page<CommentResponse> res = commentService.findComments(scheduleId, pageable);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest req,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        commentService.updateComment(scheduleId, commentId, userId, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        commentService.deleteComment(scheduleId, commentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package nbc.sma.repository;

import nbc.sma.dto.response.CommentResponse;
import nbc.sma.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new nbc.sma.dto.response.CommentResponse(c.id, new nbc.sma.dto.response.UserResponse(c.user.id, c.user.name, c.user.email), c.content, c.createdAt, c.updatedAt) FROM Comment c WHERE c.schedule.id = :scheduleId ORDER BY c.updatedAt DESC")
    Page<CommentResponse> findAllBySchedule(Long scheduleId, Pageable pageable);
}

package nbc.sma.repository;

import nbc.sma.dto.response.CommentResponse;
import nbc.sma.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 댓글 정보를 페이징한 후 CommentResponse 로 프로젝션하여 결과를 반환
     * @param scheduleId 일정 id
     * @param pageable 페이징 조건
     * @return 페이징된 CommentResponse
     */
    @Query("SELECT new nbc.sma.dto.response.CommentResponse(c.id, new nbc.sma.dto.response.UserResponse(c.user.id, c.user.name, c.user.email), c.content, c.createdAt, c.updatedAt) FROM Comment c WHERE c.schedule.id = :scheduleId ORDER BY c.updatedAt DESC")
    Page<CommentResponse> findAllBySchedule(Long scheduleId, Pageable pageable);
}

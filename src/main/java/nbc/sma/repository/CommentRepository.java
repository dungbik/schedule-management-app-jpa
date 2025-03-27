package nbc.sma.repository;

import nbc.sma.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 댓글 정보를 페이징한 후 결과를 반환
     * @param scheduleId 일정 id
     * @param pageable 페이징 조건
     * @return 페이징된 댓글 정보
     */
    Page<Comment> findByScheduleIdOrderByUpdatedAt(Long scheduleId, Pageable pageable);

}

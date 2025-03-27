package nbc.sma.repository;

import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 일정 정보를 페이징한 후 ScheduleResponse 로 프로젝션하여 결과를 반환
     * @param pageable 페이징 조건
     * @return 페이징된 ScheduleResponse
     */
    @Query("SELECT new nbc.sma.dto.response.ScheduleResponse(s.id, new nbc.sma.dto.response.UserResponse(s.user.id, s.user.name, s.user.email), s.title, s.task, s.createdAt, s.updatedAt) FROM Schedule s ORDER BY s.updatedAt DESC")
    Page<ScheduleResponse> findSchedules(Pageable pageable);
}

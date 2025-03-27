package nbc.sma.repository;

import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT new nbc.sma.dto.response.ScheduleResponse(s.id, new nbc.sma.dto.response.UserResponse(s.user.id, s.user.name, s.user.email), s.title, s.task, s.createdAt, s.updatedAt) FROM Schedule s")
    Page<ScheduleResponse> findSchedules(Pageable pageable);
}

package nbc.sma.repository;

import nbc.sma.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 일정 정보를 페이징한 후 결과를 반환
     * @param pageable 페이징 조건
     * @return 페이징된 일정 정보
     */
    Page<Schedule> findByOrderByUpdatedAt(Pageable pageable);
}

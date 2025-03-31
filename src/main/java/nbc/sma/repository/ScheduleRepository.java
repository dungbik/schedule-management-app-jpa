package nbc.sma.repository;

import nbc.sma.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 일정 정보를 페이징한 후 결과를 반환
     * @param pageable 페이징 조건
     * @return 페이징된 일정 정보
     */
    Page<Schedule> findByOrderByUpdatedAtDesc(Pageable pageable);

    @Query("SELECT s FROM Schedule s LEFT JOIN FETCH s.user u WHERE u.id = :userId AND DATE(s.updatedAt) = :updatedAt ORDER BY s.updatedAt DESC")
    Page<Schedule> findByUserIdAndUpdatedAtOrderByUpdatedAtDesc(Long userId, LocalDate updatedAt, Pageable pageable);

    @Query("SELECT s FROM Schedule s LEFT JOIN FETCH s.user u WHERE u.id = :userId ORDER BY s.updatedAt DESC")
    Page<Schedule> findByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT s FROM Schedule s LEFT JOIN FETCH s.user u WHERE DATE(s.updatedAt) = :updatedAt ORDER BY s.updatedAt DESC")
    Page<Schedule> findByUpdatedAtOrderByUpdatedAtDesc(LocalDate updatedAt, Pageable pageable);
}

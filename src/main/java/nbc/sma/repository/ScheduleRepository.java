package nbc.sma.repository;

import nbc.sma.entity.Schedule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @EntityGraph(attributePaths = {"comments"})
    Optional<Schedule> findWithCommentsById(Long id);
}

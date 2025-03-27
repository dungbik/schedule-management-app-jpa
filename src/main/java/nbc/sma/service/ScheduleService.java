package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.mapper.ScheduleMapper;
import nbc.sma.dto.request.UpdateScheduleRequest;
import nbc.sma.dto.request.ScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.SchedulesResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import nbc.sma.repository.ScheduleRepository;
import nbc.sma.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Schedule schedule = scheduleMapper.toEntity(req, user);
        scheduleRepository.save(schedule);

        return scheduleMapper.toResponse(schedule);
    }

    public SchedulesResponse findSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return scheduleMapper.toResponse(schedules);
    }

    public ScheduleResponse findSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        return scheduleMapper.toResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public void updateSchedule(Long id, UpdateScheduleRequest req) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.update(req.title(), req.task());
        scheduleRepository.save(schedule);
    }
}

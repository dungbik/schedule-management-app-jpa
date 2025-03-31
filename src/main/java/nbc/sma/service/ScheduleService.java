package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc.sma.dto.mapper.ScheduleMapper;
import nbc.sma.dto.request.SearchScheduleRequest;
import nbc.sma.dto.request.UpdateScheduleRequest;
import nbc.sma.dto.request.CreateScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import nbc.sma.exception.ForbiddenException;
import nbc.sma.exception.NotFoundException;
import nbc.sma.repository.ScheduleRepository;
import nbc.sma.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse createSchedule(Long userId, CreateScheduleRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Schedule schedule = scheduleMapper.toEntity(req, user);
        scheduleRepository.save(schedule);

        return scheduleMapper.toResponse(schedule);
    }

    public Page<ScheduleResponse> findSchedules(SearchScheduleRequest req) {
        Page<Schedule> schedulePage;
        PageRequest pageRequest = PageRequest.of(req.getPage() - 1, req.getSize());

        log.info("findSchedules {} {}", req, pageRequest);
        if (req.getUserId() != null && req.getUpdatedAt() != null) {
            schedulePage = scheduleRepository.findByUserIdAndUpdatedAtOrderByUpdatedAtDesc(req.getUserId(), req.getUpdatedAt(), pageRequest);
        } else if (req.getUserId() != null) {
            schedulePage = scheduleRepository.findByUserIdOrderByUpdatedAtDesc(req.getUserId(), pageRequest);
        } else if (req.getUpdatedAt() != null) {
            schedulePage = scheduleRepository.findByUpdatedAtOrderByUpdatedAtDesc(req.getUpdatedAt(), pageRequest);
        } else {
            schedulePage = scheduleRepository.findByOrderByUpdatedAtDesc(pageRequest);
        }

        return schedulePage.map(scheduleMapper::toResponse);
    }

    public ScheduleResponse findSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        return scheduleMapper.toResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, Long userId) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        boolean isNotOwner = !Objects.equals(userId, schedule.getUser().getId());
        if (isNotOwner) {
            throw new ForbiddenException("You are not the owner of this schedule.");
        }

        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void updateSchedule(Long id, Long userId, UpdateScheduleRequest req) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        boolean isNotOwner = !Objects.equals(userId, schedule.getUser().getId());
        if (isNotOwner) {
            throw new ForbiddenException("You are not the owner of this schedule.");
        }

        schedule.update(req.title(), req.task());
        scheduleRepository.save(schedule);
    }
}

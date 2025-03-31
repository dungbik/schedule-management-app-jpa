package nbc.sma.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.SearchScheduleRequest;
import nbc.sma.dto.request.UpdateScheduleRequest;
import nbc.sma.dto.request.CreateScheduleRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.security.SessionConst;
import nbc.sma.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest req,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
       ScheduleResponse res = scheduleService.createSchedule(userId, req);
       return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduleResponse>> findSchedules(
            @Valid @ModelAttribute SearchScheduleRequest req
    ) {
        Page<ScheduleResponse> res = scheduleService.findSchedules(req);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findSchedule(
            @PathVariable Long id
    ) {
        ScheduleResponse res = scheduleService.findSchedule(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleRequest req,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        scheduleService.updateSchedule(id, userId, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        scheduleService.deleteSchedule(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

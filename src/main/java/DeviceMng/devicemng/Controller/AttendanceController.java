package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;
import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Attendance> checkIn(@RequestParam UUID userId) {
        return ResponseEntity.ok(attendanceService.checkIn(userId));
    }

    @PostMapping("/check-out")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Attendance> checkOut(@RequestParam UUID userId) {
        return ResponseEntity.ok(attendanceService.checkOut(userId));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable UUID userId) {
        return ResponseEntity.ok(attendanceService.getUserAttendance(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Attendance>> getAlLAttendance(@RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(attendanceService.getAll(searchText));
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('USER')")
    public List<Attendance> getAttendanceByDate(
            @RequestParam UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return attendanceService.getAttendanceByDate(userId, startDate, endDate);
    }

    @GetMapping("/admin/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Attendance> getAttendanceByAdmin(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return attendanceService.getAttendanceByAdmin(userId, startDate, endDate);
    }

//    @GetMapping("/calculate-attendance")
//    public ResponseEntity<Map<String, Object>> calculateAttendance(
//            @RequestParam UUID userId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("totalWorkHours", attendanceService.calculateTotalWorkHours(userId, startDate, endDate));
//        response.put("totalWorkingDays", attendanceService.calculateTotalWorkingDays(userId, startDate, endDate));
//        response.put("totalOvertimeHours", attendanceService.calculateTotalOvertimeHours(userId, startDate, endDate));
//        response.put("validShifts", attendanceService.getValidShifts(userId, startDate, endDate));
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/summary")
    public ResponseEntity<AttendanceSummary> getAttendanceSummary(
            @RequestParam String userName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        AttendanceSummary summary = attendanceService.calculateAttendanceSummary(userName, startDate, endDate);
        return ResponseEntity.ok(summary);
    }



}

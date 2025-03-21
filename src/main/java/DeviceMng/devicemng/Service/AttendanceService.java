package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface AttendanceService extends BaseService<Attendance, UUID> {
    Attendance checkIn(UUID userId);

    Attendance checkOut(UUID userId);

    List<Attendance> getUserAttendance(UUID userId);

    List<Attendance> getAttendanceByDate(UUID userId, LocalDate startDate, LocalDate endDate);

    List<Attendance> getAttendanceByAdmin(UUID userId, LocalDate startDate, LocalDate endDate);

    AttendanceSummary calculateAttendanceSummary(String userName, LocalDate startDate, LocalDate endDate);

//    Object calculateTotalWorkHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Object calculateTotalWorkingDays(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Object calculateTotalOvertimeHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Object getValidShifts(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
}

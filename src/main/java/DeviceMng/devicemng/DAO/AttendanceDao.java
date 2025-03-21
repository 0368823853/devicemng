package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AttendanceDao extends Dao<Attendance, UUID> {

    Attendance checkIn(UUID userId);

    Attendance checkOut(UUID userId);

    List<Attendance> getUserAttendance(UUID userId);

    List<Attendance> getAttendanceByDate(UUID userId, LocalDate startDate, LocalDate endDate);

    List<Attendance> getAttendanceByAdmin(UUID userId, LocalDate startDate, LocalDate endDate);

    AttendanceSummary calculateAttendanceSummary(String userName, LocalDate startDate, LocalDate endDate);

//    Double getTotalWorkHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Double getTotalWorkingDays(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Double getTotalOvertimeHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Double getValidShifts(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
}

package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.AttendanceDao;
import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class AttendanceServiceImp implements AttendanceService {

    @Autowired
    private AttendanceDao attendanceDao;

//    @Override
//    public Optional<Attendance> findById(UUID id) {
//        return Optional.empty();
//    }

//    @Override
//    public List<Attendance> findAll(String searchText) {
//        return attendanceDao.getAllAttendance();
//    }

    @Override
    public List<Attendance> getAll(String searchText) {
        return attendanceDao.findAll(searchText);
    }

    @Override
    public Attendance getById(UUID id) {
        return null;
    }

    @Override
    public Attendance save(Attendance entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

//    @Override
//    public void deleteById(UUID id) {
//
//    }

    @Override
    public Attendance checkIn(UUID userId) {
        return attendanceDao.checkIn(userId);
    }

    @Override
    public Attendance checkOut(UUID userId) {
        return attendanceDao.checkOut(userId);
    }

    @Override
    public List<Attendance> getUserAttendance(UUID userId) {
        return attendanceDao.getUserAttendance(userId);
    }

    @Override
    public List<Attendance> getAttendanceByDate(UUID userId, LocalDate startDate, LocalDate endDate) {
        return attendanceDao.getAttendanceByDate(userId, startDate, endDate);
    }

    @Override
    public List<Attendance> getAttendanceByAdmin(UUID userId, LocalDate startDate, LocalDate endDate) {
        return attendanceDao.getAttendanceByAdmin(userId, startDate, endDate);
    }

    @Override
    public AttendanceSummary calculateAttendanceSummary(String userName, LocalDate startDate, LocalDate endDate) {
        return attendanceDao.calculateAttendanceSummary(userName, startDate, endDate);
    }

//    @Override
//    public Double calculateTotalWorkHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceDao.getTotalWorkHours(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double calculateTotalWorkingDays(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceDao.getTotalWorkingDays(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double calculateTotalOvertimeHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceDao.getTotalOvertimeHours(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double getValidShifts(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceDao.getValidShifts(userId, startDate, endDate);
//    }
}

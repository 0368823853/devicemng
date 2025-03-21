package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.AttendanceRepository;
import DeviceMng.devicemng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AttendanceDaoImp implements AttendanceDao {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    public Attendance checkIn(UUID userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        // Kiểm tra nếu đã chấm công vào nhưng chưa chấm công ra
        Optional<Attendance> existingAttendance = attendanceRepository.findByUserIdAndCheckOutIsNull(userId);
        if (existingAttendance.isPresent()) {
            throw new RuntimeException("Bạn đã chấm công vào rồi, chưa thể chấm công vào lần nữa!");
        }

        Attendance attendance = new Attendance();
        attendance.setUserId(user.getId());
        attendance.setCheckIn(LocalDateTime.now());
        attendance.setStatus("CHECKED_IN");
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setUserName(user.getUsername());

        return attendanceRepository.save(attendance);
    }

    public Attendance checkOut(UUID userId) {
        // Tìm bản ghi chấm công của user mà chưa có `checkOut`
        Attendance attendance = attendanceRepository.findByUserIdAndCheckOutIsNull(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa chấm công vào hoặc đã chấm công ra rồi!"));

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(attendance.getCheckIn(), now);

        // ✅ Kiểm tra nếu thời gian làm việc < 1 tiếng thì từ chối check-out
//        if (duration.toMinutes() < 60) {
//            throw new RuntimeException("Bạn chỉ có thể check-out sau ít nhất 1 giờ.");
//        }

        // ✅ Nếu đủ 1 tiếng thì mới được check-out
        double workHours = duration.toMinutes() / 60.0;

        attendance.setCheckOut(now);
        attendance.setStatus("CHECKED_OUT");
        attendance.setWorkHours(workHours);

        return attendanceRepository.save(attendance);
    }


    @Override
    public List<Attendance> getUserAttendance(UUID userId) {
        return attendanceRepository.findByUserId(userId);
    }

    @Override
    public List<Attendance> getAttendanceByDate(UUID userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null){
            startDate = LocalDate.now();
        }
        if (endDate == null){
            endDate = startDate;
        }
        return attendanceRepository.findByUserIdAndDateBetween(userId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
    }

    @Override
    public List<Attendance> getAttendanceByAdmin(UUID userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            startDate = LocalDate.now(); // Mặc định lấy ngày hiện tại nếu không truyền vào
        }
        if (endDate == null) {
            endDate = startDate; // Nếu không có `endDate`, lấy đúng 1 ngày
        }

        if (userId != null) {
            return attendanceRepository.findByUserIdAndDateBetween(userId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        } else {
            return attendanceRepository.findByDateBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        }
    }

//    @Override
//    public Double getTotalWorkHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceRepository.getTotalWorkHours(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double getTotalWorkingDays(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceRepository.getTotalWorkingDays(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double getTotalOvertimeHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceRepository.getTotalOvertimeHours(userId, startDate, endDate);
//    }
//
//    @Override
//    public Double getValidShifts(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
//        return attendanceRepository.getValidShifts(userId, startDate, endDate);
//    }

    @Override
    public Optional<Attendance> findById(UUID id) {
        return attendanceRepository.findById(id);
    }

    @Override
    public List<Attendance> findAll(String searchText) {
        attendanceRepository.findAll();
        return attendanceRepository.searchByName(searchText);
    }

    @Override
    public Attendance save(Attendance entity) {
        return attendanceRepository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        attendanceRepository.deleteById(id);
    }

    public AttendanceSummary calculateAttendanceSummary(String userName, LocalDate startDate, LocalDate endDate) {

        Users user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên có username: " + userName));
        // Lấy danh sách chấm công của nhân viên trong tháng
        List<Attendance> attendances = attendanceRepository.findAttendanceByUserAndDateRange(
                user.getId(),
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59) // Lấy hết ngày
        );

        // 1. Tính số ngày làm việc (đếm số ngày có check-in)
        long workDays = attendances.stream()
                .map(a -> a.getCheckIn().toLocalDate()) // Chỉ lấy ngày của check-in
                .distinct() // Lọc trùng
                .count(); // Đếm số ngày làm việc

        // 2. Tính tổng số giờ làm
        double totalHours = attendances.stream()
                .filter(a -> a.getCheckOut() != null) // Chỉ tính những ca có check-out
                .mapToDouble(a -> Duration.between(a.getCheckIn(), a.getCheckOut()).toHours()) // Tính giờ làm
                .sum();

        // 3. Tính số giờ tăng ca (làm > 8h/ngày)
        double overtimeHours = attendances.stream()
                .filter(a -> a.getCheckOut() != null) // Chỉ tính những ca có check-out
                .mapToDouble(a -> {
                    double hoursWorked = Duration.between(a.getCheckIn(), a.getCheckOut()).toHours();
                    return hoursWorked > 8 ? hoursWorked - 8 : 0; // Nếu trên 8h thì lấy phần dư làm tăng ca
                })
                .sum();

        // 4. Trả về kết quả tính toán
        return new AttendanceSummary(user.getId(), workDays, totalHours, overtimeHours);
    }
}

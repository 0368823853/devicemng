package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.SalaryDTO;
import DeviceMng.devicemng.Entity.Attendance;
import DeviceMng.devicemng.Entity.Salary;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.AttendanceRepository;
import DeviceMng.devicemng.Repository.SalaryRepository;
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
public class SalaryDaoImp implements SalaryDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public SalaryDTO calculateSalary(UUID userId, LocalDate startDate, LocalDate endDate) {
        // Lấy thông tin nhân viên
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        if (user.getBaseSalary() == null) {
            throw new RuntimeException("Base salary is null for user: " + user.getUsername());
        }

        // Lấy dữ liệu chấm công
        List<Attendance> attendances = attendanceRepository.findAttendanceByUserAndDateRange(
                userId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59)
        );

        double totalHours = 0;
        double overtimeHours = 0;

        for (Attendance a : attendances) {
            if (a.getCheckOut() == null) continue; // Bỏ qua nếu chưa check-out

            double workHours = Duration.between(a.getCheckIn(), a.getCheckOut()).toHours();
            totalHours += workHours;

            if (workHours > 8) {
                overtimeHours += (workHours - 8);
            }
        }

        // Lương theo giờ
        double hourlyRate = user.getBaseSalary() / 176;
        double overtimePay = overtimeHours * hourlyRate * 1.5;
        double totalSalary = (totalHours * hourlyRate) + overtimePay;

        // Trả về DTO, KHÔNG lưu vào DB
        return new SalaryDTO(userId, startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), totalHours, overtimeHours,overtimePay, totalSalary, user.getBaseSalary(), user.getUsername());
    }

    @Override
    public void saveSalary(SalaryDTO salaryDTO) {
        UUID userId = salaryDTO.getUserId();

        // Nếu userId bị null thì tìm theo username
        if (userId == null) {
            Users user = userRepository.findByUsername(salaryDTO.getUserName())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên có username: " + salaryDTO.getUserName()));
            userId = user.getId(); // Lấy id từ Users
        }

        Salary salary = new Salary();
        salary.setUserId(userId);
        salary.setSalaryMonth(LocalDate.of(salaryDTO.getYear(), salaryDTO.getMonth(), salaryDTO.getDay()));
        salary.setTotalHours(salaryDTO.getTotalHours());
        salary.setOvertimeHours(salaryDTO.getOvertimeHours());
        salary.setOvertimePay(salaryDTO.getOvertimePay());
        salary.setTotalSalary(salaryDTO.getTotalSalary());
        salary.setBaseSalary(userRepository.findById(userId).orElseThrow().getBaseSalary()); // Lấy baseSalary từ Users
        salary.setCreatedAt(LocalDateTime.now());
        salary.setUserName(userRepository.findById(userId).orElseThrow().getUsername());

        salaryRepository.save(salary);
    }

    private SalaryDTO convertToDTO(Salary salary) {
        return new SalaryDTO(
                salary.getUserId(),
                salary.getSalaryMonth().getYear(),
                salary.getSalaryMonth().getMonthValue(),
                salary.getSalaryMonth().getDayOfMonth(),
                salary.getTotalHours(),
                salary.getOvertimeHours(),
                salary.getOvertimePay(),
                salary.getTotalSalary(),
                salary.getBaseSalary(),
                salary.getUserName()
        );
    }

    @Override
    public List<SalaryDTO> getSalaryByUserId(UUID userId) {
        List<Salary> salaries = salaryRepository.findByUserId(userId);
        return salaries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<SalaryDTO> getAll(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return salaryRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        return salaryRepository.searchByName(searchText).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Salary> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Salary> findAll(String searchText) {
        return null;
    }

    @Override
    public Salary save(Salary entity) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}

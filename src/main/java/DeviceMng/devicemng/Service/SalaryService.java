package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.SalaryDTO;
import DeviceMng.devicemng.Entity.Salary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface SalaryService extends BaseService<Salary, UUID> {
    SalaryDTO calculateSalary(UUID userId, LocalDate startDate, LocalDate endDate);

    void saveSalary(SalaryDTO salaryDTO);

    List<SalaryDTO> getSalaryByUserId(UUID userId);
    List<SalaryDTO> getAllSalary(String searchText);

    SalaryDTO getSalaryStatistics();
}

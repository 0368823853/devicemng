package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.SalaryDTO;
import DeviceMng.devicemng.Entity.Salary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SalaryDao extends Dao<Salary, UUID> {
    SalaryDTO calculateSalary(UUID userId, LocalDate startDate, LocalDate endDate);

    void saveSalary(SalaryDTO salaryDTO);

    List<SalaryDTO> getSalaryByUserId(UUID userId);
    List<SalaryDTO> getAll(String searchText);
}

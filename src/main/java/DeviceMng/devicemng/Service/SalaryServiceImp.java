package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.SalaryDao;
import DeviceMng.devicemng.DTO.SalaryDTO;
import DeviceMng.devicemng.Entity.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class SalaryServiceImp implements SalaryService {

    @Autowired
    private SalaryDao salaryDao;

    @Override
    public SalaryDTO calculateSalary(UUID userId, LocalDate startDate, LocalDate endDate) {
        return salaryDao.calculateSalary(userId, startDate, endDate);
    }

    @Override
    public void saveSalary(SalaryDTO salaryDTO) {
        salaryDao.saveSalary(salaryDTO);
    }

    @Override
    public List<SalaryDTO> getSalaryByUserId(UUID userId) {
        return salaryDao.getSalaryByUserId(userId);
    }

    @Override
    public List<SalaryDTO> getAllSalary(String searchText) {
        return salaryDao.getAll(searchText);
    }

    @Override
    public List<Salary> getAll(String searchText) {
        return null;
    }

    @Override
    public Salary getById(UUID id) {
        return null;
    }

    @Override
    public Salary save(Salary entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

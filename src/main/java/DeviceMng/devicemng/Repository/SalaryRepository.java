package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, UUID> {
    List<Salary> findByUserId(UUID userId);

    @Query("SELECT u FROM Salary u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<Salary> searchByName(String searchText);

    @Query("SELECT SUM(s.totalSalary) FROM Salary s")
    Double getTotalSalary();

    @Query("SELECT SUM(s.totalHours) FROM Salary s")
    Double getTotalHoursWorked();

//    @Query("SELECT s.salaryMonth, SUM(s.totalSalary) FROM Salary s GROUP BY s.salaryMonth")
//    List<Object[]> getSalaryByMonth();
}

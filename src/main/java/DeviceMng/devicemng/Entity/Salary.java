package DeviceMng.devicemng.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tblsalary")
public class Salary {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "salary_month")
    private LocalDate salaryMonth;

    @Column(name = "total_hours")
    private Double totalHours;

    @Column(name = "base_salary")
    private Double baseSalary;

    @Column(name = "overtime_hours")
    private Double overtimeHours;

    @Column(name = "overtime_pay")
    private Double overtimePay;

    @Column(name = "total_salary")
    private Double totalSalary;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "user_name")
    private String userName;

    public Salary() {
    }

    public Salary(UUID id, UUID userId, LocalDate salaryMonth, Double totalHours, Double baseSalary, Double overtimeHours, Double overtimePay, Double totalSalary, LocalDateTime createdAt, String userName) {
        this.id = id;
        this.userId = userId;
        this.salaryMonth = salaryMonth;
        this.totalHours = totalHours;
        this.baseSalary = baseSalary;
        this.overtimeHours = overtimeHours;
        this.overtimePay = overtimePay;
        this.totalSalary = totalSalary;
        this.createdAt = createdAt;
        this.userName = userName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(LocalDate salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(Double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

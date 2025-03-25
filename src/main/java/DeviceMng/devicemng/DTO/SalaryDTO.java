package DeviceMng.devicemng.DTO;

import java.util.UUID;

public class SalaryDTO {
    private UUID userId;
    private int year;
    private int month;
    private int day;
    private double totalHours;
    private double overtimeHours;
    private double overtimePay;
    private double totalSalary;
    private double baseSalary;
    private String userName;

    public SalaryDTO(UUID userId, int year, int month, int day, double totalHours, double overtimeHours, double overtimePay, double totalSalary, double baseSalary, String userName) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
        this.overtimePay = overtimePay;
        this.totalSalary = totalSalary;
        this.baseSalary = baseSalary;
        this.userName = userName;
    }

    public SalaryDTO() {

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(double overtimePay) {
        this.overtimePay = overtimePay;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

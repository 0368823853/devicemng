package DeviceMng.devicemng.DTO;

import java.util.UUID;

public class AttendanceSummary {
    UUID userId;
    long workDays;
    double totalHours;
    double overtimeHours;

    public AttendanceSummary(UUID userId, long workDays, double totalHours, double overtimeHours) {
        this.userId = userId;
        this.workDays = workDays;
        this.totalHours = totalHours;
        this.overtimeHours = overtimeHours;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public long getWorkDays() {
        return workDays;
    }

    public void setWorkDays(long workDays) {
        this.workDays = workDays;
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
}

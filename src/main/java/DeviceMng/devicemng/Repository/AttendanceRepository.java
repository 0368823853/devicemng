package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.DTO.AttendanceSummary;
import DeviceMng.devicemng.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findByUserIdAndCheckOutIsNull(UUID userId);

    List<Attendance> findByUserId(UUID userId);

    @Query("SELECT a FROM Attendance a WHERE a.userId = :userId AND a.checkIn BETWEEN :startDate AND :endDate")
    List<Attendance> findByUserIdAndDateBetween(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDateTime localDateTime,
            @Param("endDate") LocalDateTime localDateTime1);

    @Query("SELECT a FROM Attendance a WHERE a.checkIn BETWEEN :startDate AND :endDate")
    List<Attendance> findByDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT u FROM Attendance u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<Attendance> searchByName(String searchText);

    @Query("SELECT a FROM Attendance a WHERE a.userId = :userId AND a.checkIn BETWEEN :startDate AND :endDate")
    List<Attendance> findAttendanceByUserAndDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

//    @Query("""
//    SELECT COALESCE(SUM(a.workHours), 0)
//    FROM Attendance a
//    WHERE a.userId = :userId
//      AND a.checkIn BETWEEN :startDate AND :endDate
//""")
//    Double getTotalWorkHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//
//    Double getTotalWorkingDays(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Double getTotalOvertimeHours(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
//
//    Double getValidShifts(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
}

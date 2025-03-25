package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, UUID> {
    List<LeaveRequest> findByUserId(UUID userId);

    @Query("SELECT u FROM LeaveRequest u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<LeaveRequest> searchByName(String searchText);

    @Query("SELECT COUNT(d) FROM LeaveRequest d")
    Long countTotalLeave();

    @Query("SELECT COUNT(d) FROM LeaveRequest d WHERE d.status = 'Pending'")
    Long countPendingLeave();

    @Query("SELECT COUNT(d) FROM LeaveRequest d WHERE d.status = 'Approved'")
    Long countApprovedLeave();

    @Query("SELECT COUNT(d) FROM LeaveRequest d WHERE d.status = 'Rejected'")
    Long countRejectedLeave();
}

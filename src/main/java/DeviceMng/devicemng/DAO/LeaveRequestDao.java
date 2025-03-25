package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.Entity.LeaveRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface LeaveRequestDao extends Dao<LeaveRequest, UUID> {
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getUserLeaveRequests(UUID userId);
    Optional<LeaveRequest> findById(UUID leaveRequestId);
    LeaveRequest save(LeaveRequest leaveRequest);
    void deleteById(UUID leaveRequestId);
    boolean existsById(UUID leaveRequestId);

    Map<String, Long> getLeaveStatistics();
}

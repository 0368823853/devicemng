package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.LeaveRequestDTO;
import DeviceMng.devicemng.DTO.LeaveStatus;
import DeviceMng.devicemng.Entity.LeaveRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface LeaveRequestService extends BaseService<LeaveRequest, UUID> {
    LeaveRequest createLeaveRequest(UUID userId, LeaveRequestDTO dto);
    LeaveRequest updateLeaveStatus(UUID leaveRequestId, String status);
    List<LeaveRequest> getUserLeaveRequests(UUID userId);
    void deleteLeaveRequest(UUID leaveRequestId, UUID userId);
    List<LeaveRequest> getAllLeaveRequests();
    void deleteLeaveRequestByAdmin(UUID leaveRequestId);
}

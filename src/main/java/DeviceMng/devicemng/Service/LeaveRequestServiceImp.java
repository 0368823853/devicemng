package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.LeaveRequestDao;
import DeviceMng.devicemng.DAO.UserDao;
import DeviceMng.devicemng.DTO.LeaveRequestDTO;
import DeviceMng.devicemng.DTO.LeaveStatus;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class LeaveRequestServiceImp implements LeaveRequestService {

    @Autowired
    private LeaveRequestDao leaveRequestDao;

    @Autowired
    private UserDao userDao;

    @Override
    public LeaveRequest createLeaveRequest(UUID userId, LeaveRequestDTO dto) {
        UserDTO user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(dto.getId());
        leaveRequest.setUserId(user.getId());
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setStatus("Pending");
        leaveRequest.getCreatedAt(LocalDateTime.now());
        leaveRequest.setUserName(user.getUsername());

        return leaveRequestDao.save(leaveRequest);
    }

    @Override
    public LeaveRequest updateLeaveStatus(UUID leaveRequestId, String status) {
        LeaveRequest leaveRequest = leaveRequestDao.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(status);
        return leaveRequestDao.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getUserLeaveRequests(UUID userId) {
        return leaveRequestDao.getUserLeaveRequests(userId);
    }

    @Override
    public void deleteLeaveRequest(UUID leaveRequestId, UUID userId) {
        LeaveRequest leaveRequest = leaveRequestDao.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        // Kiểm tra xem yêu cầu nghỉ phép có thuộc về user không
        if (!leaveRequest.getUserId().equals(userId)) {  // Sửa từ getId() thành getIduser()
            throw new RuntimeException("Unauthorized");
        }

        leaveRequestDao.deleteById(leaveRequestId);
    }


    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestDao.getAllLeaveRequests();
    }

    @Override
    public void deleteLeaveRequestByAdmin(UUID leaveRequestId) {
        if (!leaveRequestDao.existsById(leaveRequestId)) {
            throw new RuntimeException("Leave request not found");
        }
        leaveRequestDao.deleteById(leaveRequestId);
    }

    @Override
    public List<LeaveRequest> getAll(String searchText) {
        return leaveRequestDao.findAll(searchText);
    }

    @Override
    public LeaveRequest getById(UUID id) {
        return null;
    }

    @Override
    public LeaveRequest save(LeaveRequest entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

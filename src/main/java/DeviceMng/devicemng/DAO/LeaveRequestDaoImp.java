package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.LeaveRequestDTO;
import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.LeaveRepository;
import DeviceMng.devicemng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LeaveRequestDaoImp implements LeaveRequestDao {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    private LeaveRequestDTO getLeaveRequestDTO(LeaveRequest leaveRequest) {
        return new LeaveRequestDTO(
                leaveRequest.getId(),
                leaveRequest.getUserId(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getStatus(),
                leaveRequest.getCreatedAt(),
                leaveRequest.getUserName()
        );
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }

    @Override
    public List<LeaveRequest> getUserLeaveRequests(UUID userId) {
        return leaveRepository.findByUserId(userId);
    }

    @Override
    public Optional<LeaveRequest> findById(UUID leaveRequestId) {
        return leaveRepository.findById(leaveRequestId);
    }

    @Override
    public List<LeaveRequest> findAll(String searchText) {
        leaveRepository.findAll();
        return leaveRepository.searchByName(searchText);
    }

    @Override
    public LeaveRequest save(LeaveRequest leaveRequest) {
//        Users user = userRepository.findById(leaveRequest.getUserId()).get();
//
//        LeaveRequest leaveRequest1 = new LeaveRequest();
//        leaveRequest1.setId(leaveRequest.getId());
//        leaveRequest1.setUserId(user.getId());
//        leaveRequest1.setStartDate(leaveRequest.getStartDate());
//        leaveRequest1.setEndDate(leaveRequest.getEndDate());
//        leaveRequest1.setReason(leaveRequest.getReason());
//        leaveRequest1.setStatus("Pending");
//        leaveRequest1.setCreatedAt(leaveRequest.getCreatedAt());
//        leaveRequest1.setUserName(user.getUsername());
//        return leaveRepository.save(leaveRequest1);
        return leaveRepository.save(leaveRequest);
    }

    @Override
    public void deleteById(UUID leaveRequestId) {
        leaveRepository.deleteById(leaveRequestId);
    }

    @Override
    public boolean existsById(UUID leaveRequestId) {
        return leaveRepository.existsById(leaveRequestId);
    }
}

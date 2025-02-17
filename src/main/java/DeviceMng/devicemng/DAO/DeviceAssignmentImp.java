package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.Entity.DeviceAssignments;
import DeviceMng.devicemng.Repository.DeviceAssignmentRepository;
import DeviceMng.devicemng.Repository.DeviceRepository;
import DeviceMng.devicemng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DeviceAssignmentImp implements DeviceAssignmentDao{

    @Autowired
    private DeviceAssignmentRepository deviceAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceRepository deviceRepository;


    private DeviceAssignmentDTO deviceAssignmentDTO(DeviceAssignments device) {
        return new DeviceAssignmentDTO(
                device.getUserId(),
                device.getDeviceId(),
                device.getCreatedAt(),
                device.getConfirmAt(),
                device.getStatus()
        );
    }

//    @Override
//    public List<DeviceAssignments> findByUserId(UUID userId) {
//        return deviceAssignmentRepository.findByUserId(userId);
//    }

    //VietNTb: CHuyeen het validate snag service
    @Override
    public void assignDeviceToUser(UUID deviceId, UUID userId) {
        DeviceAssignments deviceAssignments = new DeviceAssignments();
        deviceAssignments.setDeviceId(deviceId);
        deviceAssignments.setUserId(userId);
        deviceAssignments.setCreatedAt(LocalDateTime.now());
        deviceAssignments.setStatus("Borrowed");
        deviceAssignmentRepository.save(deviceAssignments);
    }

    @Override
    public List<DeviceAssignmentDTO> getUserDevices(UUID userId) {
        List<DeviceAssignments> assignments = deviceAssignmentRepository.findByUserIdAndConfirmAtIsNull(userId);
        return assignments.stream()
                .map(this::deviceAssignmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void returnDevice(UUID assignmentId) {
        DeviceAssignments assignments = deviceAssignmentRepository.findByIdAndConfirmAtIsNull(assignmentId).orElse(null);
        assignments.setConfirmAt(LocalDateTime.now());
        assignments.setStatus("Returned");
        deviceAssignmentRepository.save(assignments);
    }

    //VietNTb: CHuyeen het validate snag service, xac nhan xong se xoa devicassignment
    @Override
    public void confirmDeviceReturn(UUID assignmentId) {
        DeviceAssignments assignments = deviceAssignmentRepository.findById(assignmentId).orElse(null);
        assignments.setStatus("Confirmed");
        deviceAssignmentRepository.save(assignments);
        //deviceAssignmentRepository.delete(assignments);
    }

    @Override
    public Optional<DeviceAssignments> findByDeviceIdAndUserId(UUID deviceId, UUID userId) {
        return deviceAssignmentRepository.findByDeviceIdAndUserId(deviceId, userId);
    }

    @Override
    public Optional<DeviceAssignmentDTO> findById(UUID id) {
        return deviceAssignmentRepository.findById(id).map(u -> deviceAssignmentDTO(u));
    }

    @Override
    public List<DeviceAssignmentDTO> findAll(String searchText) {
        return List.of();
    }

    @Override
    public DeviceAssignmentDTO save(DeviceAssignmentDTO entity) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }
}

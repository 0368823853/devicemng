package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.Entity.Device;
import DeviceMng.devicemng.Entity.DeviceAssignments;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Exception.DeviceNotFoundException;
import DeviceMng.devicemng.Exception.UserNotFoundException;
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
                device.getCreated_at(),
                device.getReturnedAt(),
                device.getStatus()
        );
    }

    @Override
    public void assignDeviceToUser(UUID deviceId, UUID userId) {
        Users user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found with id: "+ userId));
        Device device = deviceRepository.findById(deviceId).orElseThrow(()->new DeviceNotFoundException("Device Not Found with id: "+ deviceId));

        Optional<DeviceAssignments> existingAssignment = deviceAssignmentRepository
                .findByDeviceIdAndUserIdAndReturnedAtIsNull(deviceId, userId);

        if (existingAssignment.isPresent()) {
            throw new IllegalStateException("User đã mượn thiết bị này, không thể mượn lại!");
        }

        DeviceAssignments deviceAssignments = new DeviceAssignments();
        deviceAssignments.setDeviceId(device.getId());
        deviceAssignments.setUserId(user.getId());
        deviceAssignments.setCreated_at(LocalDateTime.now());
        deviceAssignments.setStatus("Borrowed");
        deviceAssignmentRepository.save(deviceAssignments);
    }

    @Override
    public List<DeviceAssignmentDTO> getUserDevices(UUID userId) {
        List<DeviceAssignments> assignments = deviceAssignmentRepository.findByUserIdAndReturnedAtIsNull(userId);
        return assignments.stream()
                .map(this::deviceAssignmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void returnDevice(UUID assignmentId) {
        DeviceAssignments assignments = deviceAssignmentRepository.findByIdAndReturnedAtIsNull(assignmentId)
                .orElseThrow(()->new DeviceNotFoundException("Assignment Not Found with id: "+assignmentId));
        assignments.setReturnedAt(LocalDateTime.now());
        assignments.setStatus("Returned");
        deviceAssignmentRepository.save(assignments);
    }

    @Override
    public void confirmDeviceReturn(UUID assignmentId) {
        DeviceAssignments assignments = deviceAssignmentRepository.findById(assignmentId).orElseThrow(()->new DeviceNotFoundException("Assignment Not Found with id: "+assignmentId));
        if (assignments.getReturnedAt() == null) {
            throw new IllegalArgumentException("Assignment Not Found with id: "+assignmentId);
        }
        assignments.setStatus("Confirmed");
        deviceAssignmentRepository.save(assignments);
    }

    @Override
    public Optional<DeviceAssignmentDTO> findById(UUID id) {
        return deviceAssignmentRepository.findById(id).map(u -> deviceAssignmentDTO(u));
    }

    @Override
    public List<DeviceAssignmentDTO> findAll() {
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

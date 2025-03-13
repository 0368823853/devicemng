package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.DeviceAssignmentDao;
import DeviceMng.devicemng.DAO.DeviceDao;
import DeviceMng.devicemng.DAO.UserDao;
import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.DeviceAssignments;
import DeviceMng.devicemng.Exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DeviceAssignmentServiceImp implements DeviceAssignmentService {

    @Autowired
    private DeviceAssignmentDao deviceAssignmentDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void assignDeviceToUser(UUID deviceId, UUID userId) {

        Optional<DeviceAssignments> existingAssignment = deviceAssignmentDao
                .findByDeviceId(deviceId);

        if (existingAssignment.isPresent()) {
            throw new IllegalStateException("User đã mượn thiết bị này, không thể gan them!");
        }

        DeviceDTO deviceDTO = deviceDao.findById(deviceId).orElseThrow(() -> new EntityNotFoundException("Device not found" + deviceId));
        UserDTO userDTO = userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found" + userId));
        if (deviceDTO == null) {
            throw new NotFoundException("Device assignment not found");
        }
        if (userDTO == null) {
            throw new NotFoundException("User assignment not found");
        }
        deviceAssignmentDao.assignDeviceToUser(deviceId, userId);
    }


    // TODO: them validate check user
    @Override
    public List<DeviceAssignmentDTO> getUserDevices(String username) {
        return deviceAssignmentDao.getUserDevices(username);
    }

    @Override
    public void returnDevice(UUID assignmentId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UUID uuid = (UUID) authentication.getCredentials(); // Lấy userId từ Token
//        System.out.println("User ID từ Token: " + uuid); // Kiểm tra xem đã lấy đúng chưa
//        if (uuid == null) {
//            throw new UserNotFoundException("User not found");
//        }
        deviceAssignmentDao.findById(assignmentId).orElseThrow(()->new NotFoundException("Assignment Not Found with id: "+assignmentId));
        deviceAssignmentDao.returnDevice(assignmentId);
    }

    // TODO: check validate assignmentId
    @Override
    public void confirmDeviceReturn(UUID assignmentId) {
        DeviceAssignmentDTO existingAssignment = deviceAssignmentDao.findById(assignmentId).orElseThrow(()->new NotFoundException("Assignment Not Found with id: "+assignmentId));
        if (!"Borrowed".equals(existingAssignment.getStatus())) {
            throw new NotFoundException("Assignment Not Found with id: "+assignmentId);
        }
        deviceAssignmentDao.confirmDeviceReturn(assignmentId);
    }


    @Override
    public List<DeviceAssignmentDTO> getAll(String searchText) {
        return deviceAssignmentDao.findAll(searchText);
    }

    @Override
    public DeviceAssignmentDTO getById(UUID id) {
        return deviceAssignmentDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device Assignments not found"));
    }

    @Override
    public DeviceAssignmentDTO save(DeviceAssignmentDTO entity) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}

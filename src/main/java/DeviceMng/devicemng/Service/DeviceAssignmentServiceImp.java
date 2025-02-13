package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.DeviceAssignmentDao;
import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DeviceAssignmentServiceImp implements DeviceAssignmentService {

    @Autowired
    private DeviceAssignmentDao deviceAssignmentDao;

    @Override
    public void assignDeviceToUser(UUID deviceId, UUID userId) {
        deviceAssignmentDao.assignDeviceToUser(deviceId, userId);
    }

    @Override
    public List<DeviceAssignmentDTO> getUserDevices(UUID userId) {
        return deviceAssignmentDao.getUserDevices(userId);
    }

    @Override
    public void returnDevice(UUID assignmentId) {
        deviceAssignmentDao.returnDevice(assignmentId);
    }

    @Override
    public void confirmDeviceReturn(UUID assignmentId) {
        deviceAssignmentDao.confirmDeviceReturn(assignmentId);
    }

    @Override
    public List<DeviceAssignmentDTO> getAll() {
        return List.of();
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

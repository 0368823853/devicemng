package DeviceMng.devicemng.DAO;


import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;

import java.util.List;
import java.util.UUID;


public interface DeviceAssignmentDao extends Dao<DeviceAssignmentDTO, UUID> {
    void assignDeviceToUser(UUID deviceId, UUID userId); // Admin gán thiết bị
    List<DeviceAssignmentDTO> getUserDevices(UUID userId); // User xem danh sách thiết bị của mình
    void returnDevice(UUID assignmentId); // User trả thiết bị
    void confirmDeviceReturn(UUID assignmentId);
}

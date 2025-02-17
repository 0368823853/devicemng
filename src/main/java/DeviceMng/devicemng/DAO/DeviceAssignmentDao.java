package DeviceMng.devicemng.DAO;


import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.Entity.DeviceAssignments;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface DeviceAssignmentDao extends Dao<DeviceAssignmentDTO, UUID> {
//    List<DeviceAssignments> findByUserId(UUID userId);
    void assignDeviceToUser(UUID deviceId, UUID userId); // Admin gán thiết bị
    List<DeviceAssignmentDTO> getUserDevices(UUID userId); // User xem danh sách thiết bị của mình
    void returnDevice(UUID assignmentId); // User trả thiết bị
    void confirmDeviceReturn(UUID assignmentId);

    // TODO: Chi check theo userId va deviceId
    Optional<DeviceAssignments> findByDeviceIdAndUserId(UUID deviceId, UUID userId);
}

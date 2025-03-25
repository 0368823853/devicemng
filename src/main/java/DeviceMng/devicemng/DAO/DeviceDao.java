package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.DeviceDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface DeviceDao extends Dao<DeviceDTO, UUID> {
    List<DeviceDTO> findByStatus(String status);
    List<DeviceDTO> findByNameDevice(String name);
    boolean existsByname(String name);
    DeviceDTO save(DeviceDTO deviceDTO);
    void updateDevice(UUID id, DeviceDTO deviceDTO);

    Map<String, Long> getDeviceStatistics();
}


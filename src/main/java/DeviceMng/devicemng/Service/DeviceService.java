package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface DeviceService extends BaseService<DeviceDTO, UUID> {
    //List<DeviceDTO> searchByName(String name);
    List<DeviceDTO> filterByStatus(String status);
    DeviceDTO save(DeviceDTO deviceDTO);
    void updateDevice(UUID id, DeviceDTO deviceDTO);

    Map<String, Long> getDeviceStatistics();
    //boolean existsByName(String name);
}

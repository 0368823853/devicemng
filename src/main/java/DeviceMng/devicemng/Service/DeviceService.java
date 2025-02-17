package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.DeviceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DeviceService extends BaseService<DeviceDTO, UUID> {
    //List<DeviceDTO> searchByName(String name);
    List<DeviceDTO> filterByStatus(String status);
    DeviceDTO save(DeviceDTO deviceDTO);
    DeviceDTO update(DeviceDTO entity, UUID id);
    //boolean existsByName(String name);
}

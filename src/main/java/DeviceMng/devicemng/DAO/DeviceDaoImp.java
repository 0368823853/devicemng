package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.Entity.Device;
import DeviceMng.devicemng.Repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DeviceDaoImp implements DeviceDao {

    @Autowired
    private DeviceRepository deviceRepository;

    private DeviceDTO convertDeviceDTO(Device device) {
        return new DeviceDTO(
                device.getName(),
                device.getDescription(),
                device.getStatus(),
                device.getCreated_at()
        );
    }

    @Override
    public List<DeviceDTO> findByStatus(String status) {
        return deviceRepository.findByStatus(status).stream().map(u -> convertDeviceDTO(u)).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDTO> findByNameDevice(String name) {
        return deviceRepository.findByNameContainingIgnoreCase(name).stream().map(u -> convertDeviceDTO(u)).collect(Collectors.toList());
    }

    @Override
    public boolean existsByname(String name) {
        return deviceRepository.existsByname(name);
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setName(deviceDTO.getName());
        device.setDescription(deviceDTO.getDescription());
        device.setStatus(deviceDTO.getStatus());
        if (deviceDTO.getCreatedAt() == null) {
            deviceDTO.setCreatedAt(LocalDateTime.now());
        }

        Device device1 = deviceRepository.save(device);
        return convertDeviceDTO(device1);
    }

    @Override
    public Optional<DeviceDTO> findById(UUID id) {
        return deviceRepository.findById(id).map(u -> convertDeviceDTO(u));
    }

    @Override
    public List<DeviceDTO> findAll() {
        return deviceRepository.findAll().stream().map(u -> convertDeviceDTO(u)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        // lay thong tin device can xoa
        deviceRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Device not found"));
        deviceRepository.deleteById(id);
    }
}

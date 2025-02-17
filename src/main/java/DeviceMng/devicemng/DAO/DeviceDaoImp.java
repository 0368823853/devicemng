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
                device.getCreatedAt()
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

        Device saveDevice = deviceRepository.save(device);
        return convertDeviceDTO(saveDevice);
    }

    @Override
    public Optional<DeviceDTO> findById(UUID id) {
        return deviceRepository.findById(id).map(u -> convertDeviceDTO(u));
    }

    @Override
    public List<DeviceDTO> findAll(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return deviceRepository.findAll().stream()
                    .map(this::convertDeviceDTO)
                    .collect(Collectors.toList());
        }

        return deviceRepository.searchByName(searchText).stream()
                .map(this::convertDeviceDTO)
                .collect(Collectors.toList());
    }

    //VietNTb: Valudate dua len tang sevice
    @Override
    public void deleteById(UUID id) {
        deviceRepository.deleteById(id);
    }
}

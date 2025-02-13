package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.DeviceDao;
import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.Exception.DeviceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class DeviceServiceImp implements DeviceService{

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<DeviceDTO> searchByName(String name) {
        return deviceDao.findByNameDevice(name);
    }

    @Override
    public List<DeviceDTO> filterByStatus(String status) {
        return deviceDao.findByStatus(status);
    }

    @Override
    public List<DeviceDTO> getAll() {
        return deviceDao.findAll();
    }

    @Override
    public DeviceDTO getById(UUID id) {
        return deviceDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        return deviceDao.save(deviceDTO);
    }

    @Override
    public void delete(UUID id) {
        deviceDao.deleteById(id);
    }

    @Override
    public DeviceDTO update(DeviceDTO entity, UUID id) {
        // Kiểm tra thiết bị có tồn tại không
        DeviceDTO device = getById(id);
        if (device == null) {
            throw new DeviceNotFoundException("Device not found with ID: " + id);
        }
        if (device.getName() == null || device.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên Device không được để trống hoặc chỉ chứa khoảng trắng!");
        }

        device.setName(entity.getName());
        device.setDescription(entity.getDescription());
        device.setStatus(entity.getStatus());
        if (device.getCreatedAt() == null) {
            device.setCreatedAt(LocalDateTime.now());
        }

        return deviceDao.save(device);
    }

    @Override
    public boolean existsByName(String name) {
        return deviceDao.existsByname(name);
    }

}

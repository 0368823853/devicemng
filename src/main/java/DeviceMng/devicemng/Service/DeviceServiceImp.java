package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.DeviceDao;
import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Exception.DuplicateDeviceNameException;
import DeviceMng.devicemng.Exception.NotFoundException;
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

//    @Override
//    public List<DeviceDTO> searchByName(String name) {
//        return deviceDao.findByNameDevice(name);
//    }

    @Override
    public List<DeviceDTO> filterByStatus(String status) {
        return deviceDao.findByStatus(status);
    }

    @Override
    public List<DeviceDTO> getAll(String searchText) {
        return deviceDao.findAll(searchText);
    }

    @Override
    public DeviceDTO getById(UUID id) {
        return deviceDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));
    }

    // TODO: careatedAt tu xu ly lay thoi gian hien tai de luu
    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        if (deviceDTO.getName() == null || deviceDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Device name must not be null or blank");
        }
        if (deviceDTO.getStatus() == null || deviceDTO.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input device status");
        }
        if (deviceDTO.getCreatedAt() == null) {
            deviceDTO.setCreatedAt(LocalDateTime.now());
        }
        if (deviceDao.existsByname(deviceDTO.getName())) {
            throw new DuplicateDeviceNameException("Device name already exists: " + deviceDTO.getName());
        }
        return deviceDao.save(deviceDTO);
    }

    @Override
    public void delete(UUID id) {
        deviceDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Device not found"));
        deviceDao.deleteById(id);
    }

    @Override
    public void updateDevice(UUID id, DeviceDTO deviceDTO) {
        if (deviceDTO.getName() == null || deviceDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên Device không được để trống hoặc chỉ chứa khoảng trắng!");
        }
        if (deviceDTO.getStatus() == null || deviceDTO.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Status device không được để trống!");
        }

        DeviceDTO device = deviceDao.findById(id).orElseThrow(()->new RuntimeException("Device Not Found"));
        if (device == null) {
            throw new NotFoundException("Device not found with ID: " + id);
        }
        deviceDao.updateDevice(id, deviceDTO);
    }


//    @Override
//    public boolean existsByName(String name) {
//        return deviceDao.existsByname(name);
//    }

}

package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Exception.DuplicateDeviceNameException;
import DeviceMng.devicemng.Exception.UserNotFoundException;
import DeviceMng.devicemng.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/loaddevice")
    public ResponseEntity<List<DeviceDTO>> getAllDevices(){
        return ResponseEntity.ok(deviceService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adddevice")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO){
        if (deviceDTO.getName() == null || deviceDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Device name must not be null or blank");
        }
        if (deviceDTO.getDescription() == null || deviceDTO.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Device description must not be null or blank");
        }
        if (deviceDTO.getStatus() == null || deviceDTO.getStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input device status");
        }
        if (deviceDTO.getCreatedAt() == null) {
            throw new IllegalArgumentException("Please input device created at");
        }
        if (deviceService.existsByName(deviceDTO.getName())) {
            throw new DuplicateDeviceNameException("Device name already exists: " + deviceDTO.getName());
        }
        return ResponseEntity.ok(deviceService.save(deviceDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO){
        if (deviceService.existsByName(deviceDTO.getName())) {
            throw new DuplicateDeviceNameException("Device name already exists: " + deviceDTO.getName());
        }
        return ResponseEntity.ok(deviceService.update(deviceDTO, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable UUID id){
        DeviceDTO deviceDTO = deviceService.getById(id);
        if (deviceDTO == null) {
            throw new UserNotFoundException("Device not found with ID: " + id);
        }
        deviceService.delete(id);
        return ResponseEntity.ok("Device deleted!");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/device/search")
    public ResponseEntity<List<DeviceDTO>> searchDevice(@RequestParam String name){
        List<DeviceDTO> deviceDTOS = deviceService.searchByName(name);
        if (deviceDTOS.isEmpty()) {
            throw new UserNotFoundException("No device found with name: " + name);
        }
        return ResponseEntity.ok(deviceService.searchByName(name));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/device/filter")
    public ResponseEntity<List<DeviceDTO>> filterDevice(@RequestParam String status){
        List<DeviceDTO> deviceDTOS = deviceService.filterByStatus(status);
        if (deviceDTOS.isEmpty()) {
            throw new UserNotFoundException("No device found with status: " + status);
        }
        return ResponseEntity.ok(deviceService.filterByStatus(status));
    }

}

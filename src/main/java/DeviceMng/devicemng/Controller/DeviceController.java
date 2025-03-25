package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/device")
    public ResponseEntity<List<DeviceDTO>> getAllDevices(@RequestParam(required = false) String searchText) {
        List<DeviceDTO> deviceDTO = deviceService.getAll(searchText);
        return ResponseEntity.ok(deviceDTO);
    }

    //VietNTb: CHuyen validate sang service
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adddevice")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO){
        return ResponseEntity.ok(deviceService.save(deviceDTO));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
        deviceService.updateDevice(id, deviceDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Cập nhật thiết bị thành công");
        return ResponseEntity.ok(response); // Trả về JSON thay vì text
    }


    // VietNTb: chuyen sang service
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteDevice(@PathVariable UUID id){
        deviceService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Xóa thiết bị thành công");
        return ResponseEntity.ok(response);
    }


    //VietNTb:  trung hop khoong tin thay thi tra ve rong chu khong phai loi
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/device/filter")
    public ResponseEntity<List<DeviceDTO>> filterDevice(@RequestParam String status){
        return ResponseEntity.ok(deviceService.filterByStatus(status));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getDeviceStatistics() {
        return ResponseEntity.ok(deviceService.getDeviceStatistics());
    }

}

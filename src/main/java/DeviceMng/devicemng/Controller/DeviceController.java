package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceDTO;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/loaddevice")
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

    // them validate tim id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO){
        return ResponseEntity.ok(deviceService.update(deviceDTO, id));
    }

    // VietNTb: chuyen sang service
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable UUID id){
        deviceService.delete(id);
        return ResponseEntity.ok("Device deleted!");
    }


    //VietNTb:  trung hop khoong tin thay thi tra ve rong chu khong phai loi
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/device/filter")
    public ResponseEntity<List<DeviceDTO>> filterDevice(@RequestParam String status){
        return ResponseEntity.ok(deviceService.filterByStatus(status));
    }

}

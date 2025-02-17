package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.Service.DeviceAssignmentService;
import DeviceMng.devicemng.Service.DeviceService;
import DeviceMng.devicemng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class DeviceAssignmentsController {

    @Autowired
    private DeviceAssignmentService deviceAssignmentService;

    //VietNTb: chuyeen validate sang service

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{deviceId}/assign/{userId}")
    public ResponseEntity<String> assignDevice(@PathVariable UUID deviceId, @PathVariable UUID userId) {
        deviceAssignmentService.assignDeviceToUser(deviceId, userId);
        return ResponseEntity.ok("Thiết bị đã được gán cho user thành công!");
    }

    //VietNTb: doi ten sang unasisignDeivce
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/unasisign-deivce")
    public ResponseEntity<String> unasisignDeivce(@RequestParam UUID deviceAssignmentId) {
        deviceAssignmentService.returnDevice(deviceAssignmentId);
        return ResponseEntity.ok("Bạn đã trả thiết bị thành công!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-device")
    public ResponseEntity<List<DeviceAssignmentDTO>> getUserDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getCredentials(); // Lấy userId từ Token
        System.out.println("User ID từ Token: " + userId); // Kiểm tra xem đã lấy đúng chưa
        List<DeviceAssignmentDTO> devices = deviceAssignmentService.getUserDevices(userId);
        return ResponseEntity.ok(devices);
    }


    // VietNTb: doi ten sang confirmDevice
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/confirm-device")
    public ResponseEntity<String> confirmDevice(@RequestParam UUID deviceAssignmentId) {
        deviceAssignmentService.confirmDeviceReturn(deviceAssignmentId);
        return ResponseEntity.ok("Xác nhận trả thiết bị thành công!");
    }

}

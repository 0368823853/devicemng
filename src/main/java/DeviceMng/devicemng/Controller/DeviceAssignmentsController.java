package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Exception.UserNotFoundException;
import DeviceMng.devicemng.Service.DeviceAssignmentService;
import DeviceMng.devicemng.Service.DeviceService;
import DeviceMng.devicemng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class DeviceAssignmentsController {

    @Autowired
    private DeviceAssignmentService deviceAssignmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{deviceId}/assign/{userId}")
    public ResponseEntity<String> assignDevice(@PathVariable UUID deviceId, @PathVariable UUID userId) {
        DeviceDTO deviceDTO = deviceService.getById(deviceId);
        UserDTO deviceAssignmentDTO = userService.getById(userId);
        if (deviceDTO == null) {
            throw new UserNotFoundException("Device assignment not found");
        }
        if (deviceAssignmentDTO == null) {
            throw new UserNotFoundException("User assignment not found");
        }
        deviceAssignmentService.assignDeviceToUser(deviceId, userId);
        return ResponseEntity.ok("Thiết bị đã được gán cho user thành công!");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/return")
    public ResponseEntity<String> returnDevice(@RequestParam UUID deviceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getCredentials(); // Lấy userId từ Token

        System.out.println("User ID từ Token: " + userId); // Kiểm tra xem đã lấy đúng chưa

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Nếu userId null, chặn truy cập
        }

        DeviceAssignmentDTO deviceAssignmentDTO = deviceAssignmentService.getById(deviceId);
        if (deviceAssignmentDTO == null) {
            throw new UserNotFoundException("Device assignment not found");
        }
        deviceAssignmentService.returnDevice(deviceId);
        return ResponseEntity.ok("Bạn đã trả thiết bị thành công!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-devices")
    public ResponseEntity<List<DeviceAssignmentDTO>> getUserDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) authentication.getCredentials(); // Lấy userId từ Token

        System.out.println("User ID từ Token: " + userId); // Kiểm tra xem đã lấy đúng chưa

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Nếu userId null, chặn truy cập
        }

        List<DeviceAssignmentDTO> devices = deviceAssignmentService.getUserDevices(userId);
        return ResponseEntity.ok(devices);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/confirm-return")
    public ResponseEntity<String> confirmReturn(@RequestParam UUID deviceAssignmentId) {
        DeviceAssignmentDTO deviceAssignmentDTO = deviceAssignmentService.getById(deviceAssignmentId);
        if (deviceAssignmentDTO == null) {
            throw new UserNotFoundException("Device assignment not found");
        }
        deviceAssignmentService.confirmDeviceReturn(deviceAssignmentId);
        return ResponseEntity.ok("Xác nhận trả thiết bị thành công!");
    }

}

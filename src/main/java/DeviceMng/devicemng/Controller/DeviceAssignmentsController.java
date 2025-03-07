package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceAssignmentDTO;
import DeviceMng.devicemng.DTO.DeviceDTO;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class DeviceAssignmentsController {

    @Autowired
    private DeviceAssignmentService deviceAssignmentService;

    //VietNTb: chuyeen validate sang service

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{deviceId}/assign/{userId}")
    public ResponseEntity<Map<String, String>> assignDevice(@PathVariable UUID deviceId, @PathVariable UUID userId) {
        deviceAssignmentService.assignDeviceToUser(deviceId, userId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Assign Device Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //VietNTb: doi ten sang unasisignDeivce
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/unasisign-device")
    public ResponseEntity<Map<String, String>> unasisignDeivce(@RequestParam UUID deviceAssignmentId) {
        deviceAssignmentService.returnDevice(deviceAssignmentId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Unassign Device Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/my-device")
//    public ResponseEntity<List<DeviceAssignmentDTO>> getUserDevices() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UUID userId = (UUID) authentication.getCredentials(); // Lấy userId từ Token
//        System.out.println("User ID từ Token: " + userId); // Kiểm tra xem đã lấy đúng chưa
//        List<DeviceAssignmentDTO> devices = deviceAssignmentService.getUserDevices(userId);
//        return ResponseEntity.ok(devices);
//    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-device")
    public ResponseEntity<List<DeviceAssignmentDTO>> getUserDevices(@AuthenticationPrincipal UserDetails user) {
        List<DeviceAssignmentDTO> devices = deviceAssignmentService.getUserDevices(user.getUsername());
        return ResponseEntity.ok(devices);
    }


    // VietNTb: doi ten sang confirmDevice
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/confirm-device")
    public ResponseEntity<Map<String, String>> confirmDevice(@RequestParam UUID deviceAssignmentId) {
        deviceAssignmentService.confirmDeviceReturn(deviceAssignmentId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Confirm Device Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin_device")
    public ResponseEntity<List<DeviceAssignmentDTO>> getAllDeviceAssignment(@RequestParam(required = false) String searchText) {
        List<DeviceAssignmentDTO> deviceAssignmentDTO = deviceAssignmentService.getAll(searchText);
        return ResponseEntity.ok(deviceAssignmentDTO);
    }

}

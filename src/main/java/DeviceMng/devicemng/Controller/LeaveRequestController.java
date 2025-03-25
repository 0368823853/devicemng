package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.LeaveRequestDTO;
import DeviceMng.devicemng.DTO.LeaveStatus;
import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Service.LeaveRequestService;
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
@RequestMapping("/leave")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@RequestParam UUID userId,
                                                           @RequestBody LeaveRequestDTO dto) {
        return ResponseEntity.ok(leaveRequestService.createLeaveRequest(userId, dto));
    }

    // 2. Admin duyệt hoặc từ chối nghỉ phép
    @PutMapping("/{leaveRequestId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LeaveRequest> updateLeaveStatus(@PathVariable UUID leaveRequestId,
                                                          @RequestParam String status) {
        return ResponseEntity.ok(leaveRequestService.updateLeaveStatus(leaveRequestId, status));
    }

    // 3. Nhân viên xem danh sách nghỉ phép của mình
    @GetMapping("/{userId}")
    public ResponseEntity<List<LeaveRequest>> getUserLeaveRequests(@PathVariable UUID userId) {
        return ResponseEntity.ok(leaveRequestService.getUserLeaveRequests(userId));
    }

    // 4. Nhân viên được xóa yêu cầu nghỉ phép
    @DeleteMapping("/{leaveRequestId}")
    public ResponseEntity<Map<String, String>> deleteLeaveRequest(@PathVariable UUID leaveRequestId, @RequestParam UUID userId) {
        leaveRequestService.deleteLeaveRequest(leaveRequestId, userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Leave request deleted");
        return ResponseEntity.ok(response);
    }

    // 5. Admin xem tất cả đơn nghỉ phép của nhân viên
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests(@RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(leaveRequestService.getAll(searchText));
    }

    // 6. Admin xóa đơn nghỉ phép của nhân viên
    @DeleteMapping("/{leaveRequestId}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteLeaveRequestByAdmin(@PathVariable UUID leaveRequestId) {
        leaveRequestService.deleteLeaveRequestByAdmin(leaveRequestId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Leave request deleted");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/repostLeave")
    public ResponseEntity<Map<String, Long>> getLeaveStatistics() {
        return ResponseEntity.ok(leaveRequestService.getLeaveStatistics());
    }

}

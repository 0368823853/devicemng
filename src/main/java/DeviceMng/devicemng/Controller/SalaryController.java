package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.SalaryDTO;
import DeviceMng.devicemng.Entity.LeaveRequest;
import DeviceMng.devicemng.Entity.Salary;
import DeviceMng.devicemng.Service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/calculate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaryDTO> calculateSalary(@RequestParam UUID userId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        SalaryDTO salary = salaryService.calculateSalary(userId, startDate, endDate); // Chỉ tính toán, không lưu DB
        return ResponseEntity.ok(salary);
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> confirmSalary(@RequestBody SalaryDTO salaryDTO) {
        salaryService.saveSalary(salaryDTO);
        Map<String, String> map = new HashMap<>();
        map.put("status", "Confirm Device Success");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/my-salary/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<SalaryDTO>> getMySalary(@PathVariable UUID userId) {
        return ResponseEntity.ok(salaryService.getSalaryByUserId(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SalaryDTO>> getAllSalary(@RequestParam(required = false) String searchText) {
        List<SalaryDTO> salaryDTOS = salaryService.getAllSalary(searchText);
        return ResponseEntity.ok(salaryDTOS);
    }

}

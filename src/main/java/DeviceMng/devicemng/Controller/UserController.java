package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.DeviceDTO;
import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.DTO.UserRegisterDTO;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/loaduser")
    public ResponseEntity<List<UserDTO>> getAllUser(@RequestParam(required = false) String searchText) {
        List<UserDTO> users = userService.getAll(searchText);
        return ResponseEntity.ok(users);
    }


    // VietNTb: Chuyen cac validate sang service, chuyen het validate sang 1 cho, khong dung users trong controller
    @PostMapping("/user/register")
    public ResponseEntity<?> addUser(@RequestBody UserRegisterDTO registerDTO) {
        return ResponseEntity.ok(userService.register(registerDTO));
    }

    // VietNTb: Chuyen cac validate sang service
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody Users users) {
        return ResponseEntity.ok(userService.login(users));
    }

    // VietNTb: Chuyen cac validate sang service
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id, @AuthenticationPrincipal UserDetails currentUser) {
        userService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Delete User Success");
        return ResponseEntity.ok(response);
    }

    // VietNTb: Chuyen cac validate sang service, chuyen het validate sang 1 cho
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/user/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable UUID id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return new ResponseEntity<>("User role updated!", HttpStatus.OK);
    }

    // VietNTb: Chuyen cac validate sang service, chuyen het validate sang 1 cho
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<Map<String, String>> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Update User Success");
        return ResponseEntity.ok(response);
    }

    // TODO: ca admin cung update password duoc
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/user/updatePassword")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, @AuthenticationPrincipal UserDetails user) {
        userService.updatePassword(user.getUsername(), updatePasswordDTO);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Update Password Success");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> filterByRole(@RequestParam String role) {
        List<UserDTO> users = userService.filterByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/repost")
    public ResponseEntity<Map<String, Long>> getUserStatistics() {
        return ResponseEntity.ok(userService.getUserStatistics());
    }
}

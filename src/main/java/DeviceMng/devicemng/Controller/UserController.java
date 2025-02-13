package DeviceMng.devicemng.Controller;

import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Exception.ResourceNotFoundException;
import DeviceMng.devicemng.Exception.UserNotFoundException;
import DeviceMng.devicemng.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/loaduser")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> addUser(@RequestBody Users users) {
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or blank");
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or blank");
        }
        return ResponseEntity.ok(userService.register(users));
    }
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody Users users) {
        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input username");
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input password");
        }
        return ResponseEntity.ok(userService.login(users));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id, @AuthenticationPrincipal UserDetails currentUser) {
        UserDTO user = userService.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        // Kiểm tra nếu user bị xóa là Admin
        if (user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("Không thể xóa tài khoản Admin!");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/user/{id}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable UUID id, @RequestParam String role) {
        UserDTO user = userService.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userService.updateUserRole(id, role);
        return new ResponseEntity<>("User role updated!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        UserDTO user = userService.getById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("Cap nhat nguoi dung thanh cong");
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/user/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, @AuthenticationPrincipal UserDetails user) {
        userService.updatePassword(user.getUsername(), updatePasswordDTO);
        return ResponseEntity.ok("Cap nhat mat khau thanh cong");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/search/{username}")
    public ResponseEntity<Users> findByUsername(@PathVariable String username) {
        Users user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<List<UserDTO>> filterByRole(@RequestParam String role) {
        List<UserDTO> users = userService.filterByRole(role);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with role: " + role);
        }
        return ResponseEntity.ok(users);
    }
}

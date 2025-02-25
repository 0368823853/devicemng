package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;

public class UserDTO {
    private String username;
    private String fullname;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public UserDTO(String username, String fullname, String email, String role, LocalDateTime createdAt) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

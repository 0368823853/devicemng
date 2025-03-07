package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRegisterDTO extends UserDTO {
    public UserRegisterDTO(UUID id, String username, String fullname, String email, String role, LocalDateTime createdAt) {
        super(id, username, fullname, email, role, createdAt);
    }
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(UUID id, String username, String fullname, String email, String role, LocalDateTime createdAt, String password) {
        super(id, username, fullname, email, role, createdAt);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

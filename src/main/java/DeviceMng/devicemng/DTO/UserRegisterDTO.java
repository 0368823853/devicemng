package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;

public class UserRegisterDTO extends UserDTO {
    public UserRegisterDTO(String username, String fullname, String email, String role, LocalDateTime createdAt) {
        super(username, fullname, email, role, createdAt);
    }
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String fullname, String email, String role, LocalDateTime createdAt, String password) {
        super(username, fullname, email, role, createdAt);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

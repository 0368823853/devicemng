package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public class DeviceDTO {
    private String name;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public DeviceDTO(String name, String description, String status, LocalDateTime createdAt) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

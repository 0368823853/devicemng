package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

// sua ten returnAt
public class DeviceAssignmentDTO {
    private UUID iduser;
    private UUID iddevice;
    private LocalDateTime createdAt;  // Thời gian mượn
    private LocalDateTime confirmAt;
    private String status;

    public DeviceAssignmentDTO(UUID iduser, UUID iddevice, LocalDateTime createdAt, LocalDateTime confirmAt, String status) {
        this.iduser = iduser;
        this.iddevice = iddevice;
        this.createdAt = createdAt;
        this.confirmAt = confirmAt;
        this.status = status;
    }

    public UUID getIduser() {
        return iduser;
    }

    public void setIduser(UUID iduser) {
        this.iduser = iduser;
    }

    public UUID getIddevice() {
        return iddevice;
    }

    public void setIddevice(UUID iddevice) {
        this.iddevice = iddevice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(LocalDateTime confirmAt) {
        this.confirmAt = confirmAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



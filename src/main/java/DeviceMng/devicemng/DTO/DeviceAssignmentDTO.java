package DeviceMng.devicemng.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

// sua ten returnAt
public class DeviceAssignmentDTO {
    private UUID id;
    private UUID iduser;
    private UUID iddevice;
    private String deviceName;
    private String deviceStatus;
    private LocalDateTime createdAt;  // Thời gian mượn
    private LocalDateTime confirmAt;
    private String status;

    public DeviceAssignmentDTO(UUID id, UUID iduser, UUID iddevice, String deviceName, String deviceStatus, LocalDateTime createdAt, LocalDateTime confirmAt, String status) {
        this.id = id;
        this.iduser = iduser;
        this.iddevice = iddevice;
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
        this.createdAt = createdAt;
        this.confirmAt = confirmAt;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
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



package DeviceMng.devicemng.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

// sua ten
@Entity
@Table(name = "tbldevice_assignments")
public class DeviceAssignments {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "iduser")
    private UUID userId;

    @Column(name = "iddevice")
    private UUID deviceId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "return_at")
    private LocalDateTime confirmAt;

    @Column(name = "status")
    private String status;

    public DeviceAssignments() {
    }

    public DeviceAssignments(UUID id, UUID userId, UUID deviceId, LocalDateTime createdAt, LocalDateTime confirmAt, String status) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
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

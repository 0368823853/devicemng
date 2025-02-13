package DeviceMng.devicemng.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

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
    private LocalDateTime created_at;

    @Column(name = "return_at")
    private LocalDateTime returnedAt;

    @Column(name = "status")
    private String status;

    public DeviceAssignments() {
    }

    public DeviceAssignments(UUID id, UUID userId, UUID deviceId, LocalDateTime created_at, LocalDateTime returnedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.created_at = created_at;
        this.returnedAt = returnedAt;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

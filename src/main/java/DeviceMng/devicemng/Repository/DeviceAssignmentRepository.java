package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.DeviceAssignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceAssignmentRepository extends JpaRepository<DeviceAssignments, UUID> {
    List<DeviceAssignments> findByUserId(UUID userId);
    Optional<DeviceAssignments> findByIdAndConfirmAtIsNull(UUID id);
    Optional<DeviceAssignments> findByDeviceIdAndUserId(UUID deviceId, UUID userId);
    List<DeviceAssignments> findByUserIdAndConfirmAtIsNull(UUID userId);

}

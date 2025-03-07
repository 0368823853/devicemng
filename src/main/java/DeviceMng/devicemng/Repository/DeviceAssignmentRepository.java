package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.Device;
import DeviceMng.devicemng.Entity.DeviceAssignments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT u FROM DeviceAssignments u WHERE LOWER(u.deviceName) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<DeviceAssignments> searchByName(@Param("searchText") String searchText);
}

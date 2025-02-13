package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByStatus(String status);
    List<Device> findByNameContainingIgnoreCase(String name);
    Optional<Device> findById(UUID id);
    void deleteById(UUID id);
    boolean existsByname(String name);
}

package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //dùng LOWER(...) → Để tìm kiếm không phân biệt chữ hoa/thường.
    @Query("SELECT u FROM Device u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<Device> searchByName(@Param("searchText") String searchText);
}

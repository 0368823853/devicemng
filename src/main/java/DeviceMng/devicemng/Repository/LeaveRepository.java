package DeviceMng.devicemng.Repository;

import DeviceMng.devicemng.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, UUID> {
    List<LeaveRequest> findByUserId(UUID userId);

    @Query("SELECT u FROM LeaveRequest u WHERE LOWER(u.userName) LIKE LOWER(CONCAT('%', :searchText, '%')) " )
    List<LeaveRequest> searchByName(String searchText);
}

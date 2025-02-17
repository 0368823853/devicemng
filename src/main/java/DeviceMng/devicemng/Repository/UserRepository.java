package DeviceMng.devicemng.Repository;


import DeviceMng.devicemng.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    List<Users> findByRole(String role);
    Optional<Users> findByUsername(String username);
    Optional<Users> findById(UUID id);
    void deleteById(UUID id);
    Optional<Users> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    //dùng LOWER(...) → Để tìm kiếm không phân biệt chữ hoa/thường.
    @Query("SELECT u FROM Users u WHERE LOWER(u.fullname) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%'))" +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Users> searchByFullnameOrEmail(@Param("searchText") String searchText);

}

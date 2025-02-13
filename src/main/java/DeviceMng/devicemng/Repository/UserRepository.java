package DeviceMng.devicemng.Repository;


import DeviceMng.devicemng.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
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
}

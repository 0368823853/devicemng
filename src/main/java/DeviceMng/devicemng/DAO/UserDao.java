package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.Users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao extends Dao<UserDTO, UUID> {
    List<UserDTO> findByRole(String role);
    Users findByUsername(String username);
    UserDTO save(UserDTO userDTO);
    void updateUser(UUID id, UserDTO userDTO);
    Optional<UserDTO> findByEmail(String email);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    Users register(Users users);
    String login(Users users);
    void deleteUser(UUID id);
    void updateUserRole(UUID id, String role);
    void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO);
}

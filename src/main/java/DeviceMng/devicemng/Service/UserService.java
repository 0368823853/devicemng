package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.DTO.UserRegisterDTO;
import DeviceMng.devicemng.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService extends BaseService<UserDTO, UUID> {
    //Users findByUsername(String username);
    List<UserDTO> filterByRole(String role);
    UserDTO register(UserRegisterDTO userRegisterDTO);
    void updateUser(UUID id, UserDTO userDTO);
//    Users register(Users users);
    String login(Users users);
    void deleteUser(UUID id);
    Users updateUserRole(UUID id, String role);
    void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO);
}

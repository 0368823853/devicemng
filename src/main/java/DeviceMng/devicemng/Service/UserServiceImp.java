package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.UserDao;
import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Users findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<UserDTO> filterByRole(String role) {
        return userDao.findByRole(role);
    }

    @Override
    public List<UserDTO> getAll() {
        return userDao.findAll();
    }

    @Override
    public UserDTO getById(UUID id) {
        return userDao.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        return userDao.save(userDTO);
    }

    @Override
    public void delete(UUID id) {
        userDao.deleteById(id);
    }

    @Override
    public void updateUser(UUID id, UserDTO userDTO) {
        if (userDTO.getFullname() == null || userDTO.getFullname().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống hoặc chỉ chứa khoảng trắng!");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }

        // Kiểm tra email có bị trùng không
        Optional<UserDTO> existingUser = userDao.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getEmail().equals(id)) {
            throw new IllegalArgumentException("Email đã tồn tại, vui lòng chọn email khác!");
        }
        userDao.updateUser(id, userDTO);
    }

    @Override
    public Users register(Users users) {
        return userDao.register(users);
    }

    @Override
    public String login(Users users) {
        return userDao.login(users);
    }

    @Override
    public void deleteUser(UUID id) {
        userDao.deleteById(id);
    }

    @Override
    public Users updateUserRole(UUID id, String role) {
        userDao.updateUserRole(id, role);
        return null;
    }

    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        userDao.updatePassword(username, updatePasswordDTO);
    }
}

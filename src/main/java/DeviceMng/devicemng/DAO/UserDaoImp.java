package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.UserRepository;
import DeviceMng.devicemng.Service.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserDaoImp implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    private UserDTO convertUserToDTO(Users user) {
        return new UserDTO(
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getRole(),
                user.getCreated_at()
        );
    }

    @Override
    public List<UserDTO> findByRole(String role) {
        return userRepository.findByRole(role).stream().map(u -> convertUserToDTO(u)).collect(Collectors.toList());
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findById(id).map(u -> convertUserToDTO(u));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(u -> convertUserToDTO(u)).collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setCareated_at(LocalDate.now().atStartOfDay());

        Users savedUser = userRepository.save(user);
        return convertUserToDTO(savedUser);
    }

    @Override
    public void deleteById(UUID id) {
        // Lấy thông tin user cần xóa
        Users userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        // Nếu user cần xóa là Admin => Không cho xóa
        if (userToDelete.getRole().equals("ADMIN")) {
            throw new RuntimeException("Không thể xóa người dùng có quyền ADMIN");
        }

        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(UUID id, UserDTO userDTO) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getFullname() != null) {
            user.setFullname(userDTO.getFullname());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        userRepository.save(user);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(u -> convertUserToDTO(u));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Users register(Users users) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.existsByUsername(users.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(users.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // Mã hóa mật khẩu trước khi lưu
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

        // Nếu role bị null, tự động đặt "USER"
        if (users.getRole() == null) {
            users.setRole("USER");
        }
        return userRepository.save(users);
    }

    @Override
    public String login(Users users) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        if (authentication.isAuthenticated()) {
            return loginTokenService.createLoginToken(users.getUsername());
        }
        return "Faile";
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserRole(UUID id, String role) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        user.setRole(role);

        if(!role.equals("ADMIN") && !role.equals("USER")) {
            throw new RuntimeException("Role khong hop le");
        }
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        Users user = userRepository.findByUsername(username).orElse(null);
        if (!bCryptPasswordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mat khau cu khong dung");
        }
        user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

}

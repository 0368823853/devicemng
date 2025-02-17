package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.DTO.UpdatePasswordDTO;
import DeviceMng.devicemng.DTO.UserDTO;
import DeviceMng.devicemng.DTO.UserRegisterDTO;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserDaoImp implements UserDao {

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    private UserDTO convertUserToDTO(Users user) {
        return new UserDTO(
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
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
    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        Users user = new Users();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        user.setFullname(userRegisterDTO.getFullname());
        user.setEmail(userRegisterDTO.getEmail());
        // Nếu role bị null, tự động đặt "USER"
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        user.setCreatedAt(LocalDateTime.now());

        Users savedUser = userRepository.save(user);
        return convertUserToDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findById(id).map(u -> convertUserToDTO(u));
    }

    @Override
    public List<UserDTO> findAll(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return userRepository.findAll().stream()
                    .map(this::convertUserToDTO)
                    .collect(Collectors.toList());
        }

        return userRepository.searchByFullnameOrEmail(searchText).stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(UserDTO entity) {
        return null;
    }

    // chuyen valideta sang service
    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


    // validate sang service, bo cac dieu kien
    @Override
    public void updateUser(UUID id, UserDTO userDTO) {
        Users user = userRepository.findById(id).orElse(null);
        user.setUsername(userDTO.getUsername());
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
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

    // dua loginn ve service

    //VietNTb: chuyen admin user thanh hang so de dung chung, chuyen validate ve service
    @Override
    public void updateUserRole(UUID id, String role) {
        Users user = userRepository.findById(id).orElse(null);
        user.setRole(role);
        userRepository.save(user);
    }

    //VietntB; CHUYEN VALISATE VE service
    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        Users user = userRepository.findByUsername(username).orElse(null);
        user.setPassword(bCryptPasswordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

}

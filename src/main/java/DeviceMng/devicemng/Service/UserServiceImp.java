package DeviceMng.devicemng.Service;

import DeviceMng.devicemng.DAO.UserDao;
import DeviceMng.devicemng.DTO.*;
import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    // chuyen ve service
    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);


//    @Override
//    public Users findByUsername(String username) {
//        return userDao.findByUsername(username);
//    }

    // TODO: neu khong co gi thi tra ve mang rong
    @Override
    public List<UserDTO> filterByRole(String role) {
        return userDao.findByRole(role);
    }

    @Override
    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO.getUsername() == null || userRegisterDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or blank");
        }
        if (userRegisterDTO.getEmail() == null || userRegisterDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or blank");
        }
        if (userDao.existsByUsername(userRegisterDTO.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if (userDao.existsByEmail(userRegisterDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        return userDao.register(userRegisterDTO);
    }

    @Override
    public List<UserDTO> getAll(String searchText) {
        return userDao.findAll(searchText);
    }

    @Override
    public UserDTO getById(UUID id) {
        return userDao.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
    }

    // TODO: them validate
    @Override
    public UserDTO save(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống hoặc chỉ chứa khoảng trắng!");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }
        if (userDao.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }
        if (userDao.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        return userDao.save(userDTO);
    }

    //TODO: them validate
    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        userDao.deleteById(id);
    }

    //VietNTb: chi check name, email,
    @Override
    public void updateUser(UUID id, UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống hoặc chỉ chứa khoảng trắng!");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }
        if (!userDTO.getRole().equals(UserRole.ADMIN)  && !userDTO.getRole().equals(UserRole.USER)) {
            throw new RuntimeException("Role không hợp lệ");
        }
        UserDTO user = userDao.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }

        userDao.updateUser(id, userDTO);
    }

    @Override
    public String login(Users users) {

        if (users.getUsername() == null || users.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input username");
        }
        if (users.getPassword() == null || users.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Please input password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Lấy role của user từ Authentication
            String role = authentication.getAuthorities().iterator().next().getAuthority();

            Users usersFormDB = userDao.findByUsername(users.getUsername());

            // truyền role vào token
            return loginTokenService.createLoginToken(usersFormDB.getUsername(), role, usersFormDB.getId());
        }

        return "False";
    }


    @Override
    public void deleteUser(UUID id) {

        UserDTO user = userDao.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        // Kiểm tra nếu user bị xóa là Admin
        if (user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("Không thể xóa tài khoản Admin!");
        }
        userDao.deleteById(id);
    }

    @Override
    public Users updateUserRole(UUID id, String role) {
        UserDTO user = userDao.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        if (!role.equals(UserRole.ADMIN) && !role.equals(UserRole.USER)) {
            throw new RuntimeException("Role không hợp lệ");
        }
        userDao.updateUserRole(id, role);
        return null;
    }

    @Override
    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) {
        Users user = userDao.findByUsername(username);
        if (!bCryptPasswordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mat khau cu khong dung");
        }
        userDao.updatePassword(username, updatePasswordDTO);
    }

    @Override
    public UUID getUserIdByUsername(String username) {
        userDao.findByUsername(username);
        return null;
    }

    @Override
    public Map<String, Long> getUserStatistics() {
        return userDao.getUserStatistics();
    }

//    @Override
//    public EmployeeReportDTO getEmployeeReport() {
//        long totalEmployees = userDao.count();
//        long totalAdmins = userDao.countByRole("ADMIN");
//        long newEmployees = userDao.countNewEmployeesThisMonth();
//        long employeesLeft = userDao.countEmployeesLeftThisMonth();
//
//        return new EmployeeReportDTO(totalEmployees, totalAdmins, newEmployees, employeesLeft);
//    }
}

package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.Entity.Users;
import DeviceMng.devicemng.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElse(null);
        if(users == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserSecutityImp(users);
    }
}

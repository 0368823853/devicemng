package DeviceMng.devicemng.DAO;

import DeviceMng.devicemng.Entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


public class UserSecutityImp implements UserDetails {
    private Users users;

    public UserSecutityImp(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // mặc định Spring yêu cầu role phải có tiền tố "ROLE_".
        return List.of(new SimpleGrantedAuthority("ROLE_" +users.getRole()));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }
}

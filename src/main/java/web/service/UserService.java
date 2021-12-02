package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> allread();
    User save(User user);
    User show (long id);
    boolean update(User usernew);
    boolean delete(long id);

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}

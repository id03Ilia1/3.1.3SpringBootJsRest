package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import web.repositories.RoleRepository;
import web.repositories.UserRepository;


import java.util.List;

@Service("userDetaiServiceImpl")
public class UserDetailsServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.Username(username);
    }

    @Override
    @Transactional
    public List<User> allread() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User show(long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public boolean update(User usernew) {
        if (usernew == null) {
            return false;
        }
        usernew.setPassword(passwordEncoder.encode(usernew.getPassword()));
        userRepository.save(usernew);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (show(id) == null) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}

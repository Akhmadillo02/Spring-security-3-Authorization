package uz.ahmadillo02.springsecurity3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ahmadillo02.springsecurity3.domein.User;
import uz.ahmadillo02.springsecurity3.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User save(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}

package uz.ahmadillo02.springsecurity3.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.ahmadillo02.springsecurity3.domein.Authority;
import uz.ahmadillo02.springsecurity3.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCaseUserName = username.toLowerCase();
        return userRepository.findByUsername(lowerCaseUserName)
                .map(user -> creatSpringSecurityUser(lowerCaseUserName, user))
                .orElseThrow(() -> new UserNotActivateException("User " + username + "was not found in the database"));
    }

    private User creatSpringSecurityUser(String username, uz.ahmadillo02.springsecurity3.domein.User user) {
        if (!user.isActivated()) {
            throw new UserNotActivateException("User " + username + "was not activated");
        }
        List<GrantedAuthority>grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(username, user.getPassword(), grantedAuthorities);
    }
}

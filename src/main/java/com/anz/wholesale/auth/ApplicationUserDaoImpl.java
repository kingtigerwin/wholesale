package com.anz.wholesale.auth;

import com.anz.wholesale.entities.Authority;
import com.anz.wholesale.entities.User;
import com.anz.wholesale.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<ApplicationUserDetails> fetchUserByUsername(String username) {
        // An unknown username yields Optional.empty(), which ApplicationUserService turns into UsernameNotFoundException.
        return userRepository.findByUsername(username)
                .map(this::toApplicationUserDetails);
    }

    private ApplicationUserDetails toApplicationUserDetails(User user) {
        return new ApplicationUserDetails(
                user.getUsername(),
                user.getEncodedPassword(),
                getGrantedAuthorities(user.getAuthorities()),
                true,
                true,
                true,
                true
        );
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        Set<SimpleGrantedAuthority> permissions = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
        return permissions;
    }
}

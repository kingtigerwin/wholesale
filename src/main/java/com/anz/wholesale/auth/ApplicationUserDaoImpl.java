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
        User user = userRepository.findByUsername(username);

        ApplicationUserDetails applicationUserDetails = new ApplicationUserDetails(
                user.getUsername(),
                user.getEncodedPassword(),
                getGrantedAuthorities(user.getAuthorities()),
                true,
                true,
                true,
                true
        );
        return Optional.of(applicationUserDetails);
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        Set<SimpleGrantedAuthority> permissions = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
        return permissions;
    }
}

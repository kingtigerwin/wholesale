package com.anz.wholesale.repositories;

import com.anz.wholesale.WholesaleApplication;
import com.anz.wholesale.entities.Authority;
import com.anz.wholesale.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WholesaleApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    public void shouldReturnUserGivenCorrectUsername() {

        // Create a new user.
        User user = new User();
        user.setUsername("tigerqian");
        user.setEncodedPassword(passwordEncoder.encode("password"));
        User savedUser = userRepository.save(user);


        // Create a new authority associated with the above user.
        Authority authority1 = new Authority();
        authority1.setPermission("ROLE_ADMIN");
        authorityRepository.save(authority1);

        // Create one more authority associated with the above user.
        Authority authority2 = new Authority();
        authority2.setPermission("ROLE_GROUPADMIN");
        authorityRepository.save(authority2);

        savedUser.setAuthorities(Set.of(authority1, authority2));
        userRepository.save(savedUser);


        User savedUserWithAuthorities = userRepository.findByUsername(savedUser.getUsername());
        assertNotNull(savedUserWithAuthorities);
        assertEquals(2, savedUserWithAuthorities.getAuthorities().size());
    }
}

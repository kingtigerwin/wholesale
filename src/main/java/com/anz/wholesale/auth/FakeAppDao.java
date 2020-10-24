package com.anz.wholesale.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.anz.wholesale.security.ApplicationUserRole.*;

@Repository
@RequiredArgsConstructor
public class FakeAppDao implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUserDetails> fetchUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUserDetails -> applicationUserDetails.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUserDetails> getApplicationUsers() {
        List<ApplicationUserDetails> applicationUserDetailsList = List.of(
                new ApplicationUserDetails(
                        "tiger",
                        passwordEncoder.encode("tiger"),
                        ACCOUNT.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUserDetails(
                        "cathy",
                        passwordEncoder.encode("cathy"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUserDetails(
                        "sisi",
                        passwordEncoder.encode("sisi"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );
        return applicationUserDetailsList;
    }
}

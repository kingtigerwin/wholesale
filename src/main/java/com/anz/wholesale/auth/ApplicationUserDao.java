package com.anz.wholesale.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUserDetails> fetchUserByUsername(String username);
}


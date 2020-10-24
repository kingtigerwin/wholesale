package com.anz.wholesale.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.anz.wholesale.security.ApplicationUserPermission.ACCOUNT_READ;
import static com.anz.wholesale.security.ApplicationUserPermission.ACCOUNT_WRITE;

public enum ApplicationUserRole {
    ACCOUNT(Set.of()),
    ADMIN(Set.of(ACCOUNT_READ, ACCOUNT_WRITE)),
    ADMINTRAINEE(Set.of(ACCOUNT_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}

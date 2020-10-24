package com.anz.wholesale.security;

public enum ApplicationUserPermission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}

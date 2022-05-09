package com.kgc.sauw.os.user;

import com.jvmfrog.curve.registry.RegistryObject;

public class User extends RegistryObject {
    private final String homeDir;
    private String password;

    public User(String username) {
        homeDir = "/home/" + username;
    }

    @Override
    public void init() {
    }
}

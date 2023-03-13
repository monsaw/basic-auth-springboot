package com.example.demo.config;

public final class Endpoints {
    public static final String[] SECURED_ENDPOINT = {
            "/library/"
    };

    public static final String[] SECURED_ENDPOINT_USER = {
            "/library/delete/{id}"
    };
    public static final String[] UNSECURED_ENDPOINT = {
            "/users/**"
    };
}

package com.cloudpi.cloudpi_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainTest {

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123"));
    }
}

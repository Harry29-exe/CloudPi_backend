package com.cloudpi.cloudpi_backend.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemPreferences {
    public Boolean userManagementLANOnly;

    public SystemPreferences(@Value("${cloud.pi.config.modifications-only-from-local-network}") String fromLocalHostOnly) {
        userManagementLANOnly = fromLocalHostOnly.equals("true");
        System.out.println("\n" + fromLocalHostOnly + "\n" + userManagementLANOnly + "\n");
    }

    public Boolean getUserManagementLANOnly() {
        return userManagementLANOnly;
    }
}
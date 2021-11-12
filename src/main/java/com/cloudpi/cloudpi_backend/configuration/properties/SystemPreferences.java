package com.cloudpi.cloudpi_backend.configuration.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemPreferences {
    public Boolean userManagementLANOnly;

    public SystemPreferences(@Value("${cloud-pi.security.modifications-only-from-local-network}") String fromLocalHostOnly) {
        userManagementLANOnly = fromLocalHostOnly.equals("true");
    }

    public Boolean getUserManagementLANOnly() {
        return userManagementLANOnly;
    }
}

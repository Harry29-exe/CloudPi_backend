package com.cloudpi.cloudpi_backend.configuration.authorization;

import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthorityPermission;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Configuration
public class CPAuthoritiesAggregator {

    @Bean
    public Map<String, CloudPiRole> getAllRoles() {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var roleClasses = reflections.getTypesAnnotatedWith(Roles.class);

        Map<String, CloudPiRole> roleMap = new HashMap<>();
        Map<String, Class<?>> roleDuplication = new HashMap<>();

        for(var roleClass : roleClasses) {
            var roles = getValueOfClassStaticFieldsOfType(roleClass, CloudPiRole.class);
            for(var role : roles) {
                if(roleMap.containsKey("ROLE_" + role.name())) {
                    throw new IllegalStateException("Roles has to have unique names but there" +
                            "were find roles with overlapping names in classes:" +
                            roleDuplication.get("ROLE_" + role.name()).toString() +
                            roleClass
                    );
                }
                roleMap.put("ROLE_" + role.name(), role);
                roleDuplication.put("ROLE_" + role.name(), roleClass);
            }
        }

        return roleMap;
    }

    @Bean
    public Map<String, CPAuthorityPermission> getAllPermissions() {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var permissionClasses = reflections.getTypesAnnotatedWith(Permissions.class);

        Map<String, CPAuthorityPermission> permissionMap = new HashMap<>();
        Map<String, Class<?>> permissionsDuplications = new HashMap<>();

        for(var permissionClass : permissionClasses) {
            var permissions = getValueOfClassStaticFieldsOfType(permissionClass, CPAuthorityPermission.class);
            for(var permission : permissions) {
                if(permissionMap.containsKey(permission.getAuthority())) {
                    throw new IllegalStateException("Permissions has to have unique names but there" +
                            "were find permissions with overlapping names in classes:" +
                            permissionsDuplications.get(permission.getAuthority()).toString() +
                            permissionClass
                    );
                }
                permissionMap.put(permission.getAuthority(), permission);
                permissionsDuplications.put(permission.getAuthority(), permissionClass);
            }
        }

        return permissionMap;
    }

    private static <T> List<T> getValueOfClassStaticFieldsOfType(Class<?> clazz, Class<? extends T> type) {
        List<T> roles = new ArrayList<>();
        for(Field field : clazz.getFields()) {

            if(Modifier.isStatic(field.getModifiers()) &&
                    type.isAssignableFrom(field.getType()))
            {
                try {
                    roles.add( (T) field.get(null) );
                } catch (Exception ex) {
                    throw new IllegalStateException("Something went very wrong.");
                }
            }
        }

        return roles;
    }


    public static void main(String[] args) {
        var temp = new CPAuthoritiesAggregator();
        temp.getAllRoles().values().forEach(role -> System.out.println(role.name()));
    }

    private static class RoleNameWithClass {
        public final String role;
        public final Class<? extends Enum<?>> clazz;

        public RoleNameWithClass(String role, Class<? extends Enum<?>> clazz) {
            this.role = role;
            this.clazz = clazz;
        }
    }
}

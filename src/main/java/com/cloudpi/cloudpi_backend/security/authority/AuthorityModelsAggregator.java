package com.cloudpi.cloudpi_backend.security.authority;

import com.cloudpi.cloudpi_backend.authorization.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorization.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Role;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Stream;

public class AuthorityModelsAggregator {
    private static ImmutableSortedMap<String, RoleModel> roleModelMap = null;
    private static ImmutableSortedMap<String, PermissionModel> permissionModelMap = null;

    public static RoleModel getRoleModelByRoleName(String roleName) {
        initIfNeeded();
        return roleModelMap.get(roleName);
    }

    public static Collection<RoleModel> getRolesByRoleNames(String... names) {
        initIfNeeded();
        ArrayList<RoleModel> roles = new ArrayList<>(names.length);
        for( String name : names) {
            roles.add(roleModelMap.get(name));
        }

        return roles;
    }

    public static Collection<RoleModel> getRolesByRoleNames(Stream<String> names) {
        initIfNeeded();
        ArrayList<RoleModel> roles = new ArrayList<>();
        names.forEach(roleName ->
            roles.add(roleModelMap.get(roleName)));

        return roles;
    }

    public static Collection<RoleModel> getRolesByRoleEntities(Collection<RoleEntity> roleEntities) {
        initIfNeeded();
        ArrayList<RoleModel> roles = new ArrayList<>(roleEntities.size());
        roleEntities.forEach(roleName ->
                roles.add(roleModelMap.get(roleName.getRole())));

        return roles;
    }

    public static PermissionModel getPermissionByName(String permissionName) {
        initIfNeeded();
        return permissionModelMap.get(permissionName);
    }

    public static Collection<PermissionModel> getPermissionsByNames(String... permissionNames) {
        initIfNeeded();
        ArrayList<PermissionModel> permissions = new ArrayList<>(permissionNames.length);
        for(String name : permissionNames) {
            permissions.add(permissionModelMap.get(name));
        }
        return permissions;
    }

    public static Collection<PermissionModel> getPermissionsByNames(Stream<String> permissionNames) {
        initIfNeeded();
        ArrayList<PermissionModel> permissions = new ArrayList<>();
        permissionNames.forEach(permissionName ->
            permissions.add(permissionModelMap.get(permissionName)));

        return permissions;
    }

    public static Collection<PermissionModel> getPermissionsByPermissionEntities(Collection<PermissionEntity> permissionEntities) {
        initIfNeeded();
        ArrayList<PermissionModel> permissions = new ArrayList<>(permissionEntities.size());

        permissionEntities.forEach(permissionEntity ->
                permissions.add(permissionModelMap.get(permissionEntity.getAuthority())));

        return permissions;
    }

    private static void initIfNeeded() {
        if(permissionModelMap == null || roleModelMap == null) {
            try {
                permissionModelMap = getAllPermissions();
                roleModelMap = getAllRoles();
                checkIntegrityOfAuthorities();
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("This should have never happen");
            }
        }
    }

    private static void checkIntegrityOfAuthorities() {
        for(var roleModel : roleModelMap.values()) {
            for(var canBeGivenBy : roleModel.mayBeGivenBy()) {
                if(!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                    || !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Role: " + roleModel.getName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }

        for(var permissionModel : permissionModelMap.values()) {
            for(var canBeGivenBy : permissionModel.mayBeGivenBy()) {
                if(!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                        || !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Permission: " + permissionModel.getName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }
    }

    private static ImmutableSortedMap<String, RoleModel> getAllRoles() throws IllegalAccessException {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var roleClasses = reflections.getSubTypesOf(RoleClass.class);

        Map<String, RoleModel> roleMap = new HashMap<>();
        Map<String, Class<? extends  RoleClass>> duplicationCheckMap = new HashMap<>();

        for(var roleClass : roleClasses) {
            var fields = getAllRoleFields(roleClass);
            for(var field : fields) {
                RoleClass value = (RoleClass) field.get(null);
                var fieldAnnotation = field.getAnnotation(Role.class);
                var roleName = value.getRoleName();

                if(duplicationCheckMap.containsKey(roleName)) {
                    throw new IllegalStateException(
                            "Permission with name: " + roleName +
                            "exist in class: " + duplicationCheckMap.get(roleName).getName() +
                            " and  in class: " + roleClass.getName());
                }
                duplicationCheckMap.put(roleName, roleClass);
                roleMap.put(roleName, new SimpleRoleModel(roleName,
                        fieldAnnotation.permissions(),
                        fieldAnnotation.mayBeGivenBy()));
            }

        }

        return ImmutableSortedMap.copyOf(roleMap);
    }

    private static ImmutableSortedMap<String, PermissionModel> getAllPermissions() throws IllegalAccessException {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var permissionClasses = reflections.getSubTypesOf(PermissionClass.class);

        Map<String, PermissionModel> permissionMap = new HashMap<>();
        Map<String, Class<? extends PermissionClass>> duplicatesCheckMap = new HashMap<>();

        for(var permissionClass : permissionClasses) {
            var fields = getAllPermissionFields(permissionClass);
            for(var field : fields) {
                var value = (PermissionClass) field.get(null);
                var permissionName = value.getPermissionName();
                var permissionAnnotation = field.getAnnotation(Permission.class);
                if(duplicatesCheckMap.containsKey(permissionName)) {
                    throw new IllegalStateException(
                            "Permission with name: " + permissionName +
                            "exist in class: " + duplicatesCheckMap.get(permissionName).getName() +
                            " and  in class: " + permissionClass.getName());
                }
                duplicatesCheckMap.put(permissionName, permissionClass);
                permissionMap.put(permissionName, new SimplePermissionModel(
                        permissionName,
                        permissionAnnotation.mayBeGivenBy()
                ));
            }
        }

        return ImmutableSortedMap.copyOf(permissionMap);
    }




    private static List<Field> getAllPermissionFields(Class<? extends PermissionClass> permissionClass) {
        LinkedList<Field> fields = new LinkedList<>();
        for(var field : permissionClass.getFields()) {
            if(field.isAnnotationPresent(Permission.class) &&
                    PermissionClass.class.isAssignableFrom(field.getDeclaringClass()) &&
                    (field.isEnumConstant() || Modifier.isStatic(field.getModifiers()))
            ) {
                fields.addLast(field);
            }
        }

        return fields;
    }

    private static List<Field> getAllRoleFields(Class<? extends RoleClass> roleClass) {
        LinkedList<Field> fields = new LinkedList<>();
        for(var field : roleClass.getFields()) {
            if (field.isAnnotationPresent(Role.class) &&
                    RoleClass.class.isAssignableFrom(field.getDeclaringClass()) &&
                    (field.isEnumConstant() || Modifier.isStatic(field.getModifiers()))
            ) {
                fields.addLast(field);
            }
        }

        return fields;
    }
}

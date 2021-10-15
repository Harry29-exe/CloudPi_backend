package com.cloudpi.cloudpi_backend.security.authority;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.security.authority.annotations.Permission;
import com.google.common.collect.ImmutableSortedMap;

import java.util.*;
import java.util.stream.Stream;

public class AuthorityModelsAggregator {
    private static final ImmutableSortedMap<String, RoleModel> roleModelMap;
    private static final ImmutableSortedMap<String, PermissionModel> permissionModelMap;

    static {
        var scanner = new AuthorityModelScanner();
        roleModelMap = scanner.getRoleModels();
        permissionModelMap = scanner.getPermissionModels();
        checkIntegrityOfAuthorities();
    }

    public static RoleModel getRoleModelByRoleName(String roleName) {
        return roleModelMap.get(roleName);
    }

    public static Collection<RoleModel> getRolesByRoleNames(String... names) {
        ArrayList<RoleModel> roles = new ArrayList<>(names.length);
        for (String name : names) {
            roles.add(roleModelMap.get(name));
        }

        return roles;
    }

    public static Collection<RoleModel> getRolesByRoleNames(Stream<String> names) {
        ArrayList<RoleModel> roles = new ArrayList<>();
        names.forEach(roleName ->
                roles.add(roleModelMap.get(roleName)));

        return roles;
    }

    public static Collection<RoleModel> getRolesByRoleEntities(Collection<RoleEntity> roleEntities) {
        ArrayList<RoleModel> roles = new ArrayList<>(roleEntities.size());
        roleEntities.forEach(roleName ->
                roles.add(roleModelMap.get(roleName.getRole())));

        return roles;
    }

    public static PermissionModel getPermissionByName(String permissionName) {
        return permissionModelMap.get(permissionName);
    }

    public static Collection<PermissionModel> getPermissionsByNames(String... permissionNames) {
        ArrayList<PermissionModel> permissions = new ArrayList<>(permissionNames.length);
        for (String name : permissionNames) {
            permissions.add(permissionModelMap.get(name));
        }
        return permissions;
    }

    public static Collection<PermissionModel> getPermissionsByNames(Stream<String> permissionNames) {
        ArrayList<PermissionModel> permissions = new ArrayList<>();
        permissionNames.forEach(permissionName ->
                permissions.add(permissionModelMap.get(permissionName)));

        return permissions;
    }

    public static Collection<PermissionModel> getPermissionsByPermissionEntities(Collection<PermissionEntity> permissionEntities) {
        ArrayList<PermissionModel> permissions = new ArrayList<>(permissionEntities.size());

        permissionEntities.forEach(permissionEntity ->
                permissions.add(permissionModelMap.get(permissionEntity.getAuthority())));

        return permissions;
    }

    static void checkIntegrityOfAuthorities() {
        for (var roleModel : roleModelMap.values()) {
            for (var canBeGivenBy : roleModel.mayBeGivenBy()) {
                if (!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                        || !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Role: " + roleModel.getAuthorityName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }

        for (var permissionModel : permissionModelMap.values()) {
            for (var canBeGivenBy : permissionModel.mayBeGivenBy()) {
                if (!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                        || !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Permission: " + permissionModel.getAuthorityName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }
    }

}

package com.cloudpi.cloudpi_backend.security.authority_system;

import com.cloudpi.cloudpi_backend.authorities.entities.PermissionEntity;
import com.cloudpi.cloudpi_backend.authorities.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.user.enpoints.AccountType;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;

import java.util.*;
import java.util.stream.Stream;

public class AuthorityModelsAggregator {
    private static final ImmutableSortedMap<String, RoleModel> roleModelMap;
    private static final ImmutableSortedMap<String, PermissionModel> permissionModelMap;
    private static final ImmutableList<AuthorityModel> defaultAuthoritiesOf_USER;
    private static final ImmutableList<AuthorityModel> defaultAuthoritiesOf_ROOT;
    private static final ImmutableList<AuthorityModel> defaultAuthoritiesOf_SERVICE_WORKER;


    static {
        var scanner = new AuthorityModelScanner();
        roleModelMap = scanner.getRoleModels();
        permissionModelMap = scanner.getPermissionModels();

        checkIntegrityOfAuthorities();
    }

    public static ImmutableCollection<RoleModel> getAllRoles() {
        return roleModelMap.values();
    }

    public static ImmutableCollection<PermissionModel> getAllPermissions() {
        return permissionModelMap.values();
    }

    public static RoleModel getRoleModelByRoleName(String roleName) {
        return roleModelMap.get(roleName);
    }

    public static Collection<AuthorityModel> getDefaultAuthorities(String accountType) {
        return switch (accountType) {
            case AccountType.USER -> defaultAuthoritiesOf_USER;
            case AccountType.ROOT -> defaultAuthoritiesOf_ROOT;
            case AccountType.SERVICE_WORKER ->  defaultAuthoritiesOf_SERVICE_WORKER;
            default -> throw new IllegalArgumentException("Method parameter \"accountType\" must represents one of AccountType values");
        };
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

    static {
        List<AuthorityModel> USER = new ArrayList<>();
        List<AuthorityModel> ROOT = new ArrayList<>();
        List<AuthorityModel> SERVICE_WORKER = new ArrayList<>();

        for(var role : roleModelMap.values()) {
            for(var accountType : role.getAccountsThatHaveItByDefault()) {
                switch (accountType) {
                    case "USER" -> USER.add(role);
                    case "ROOT" -> ROOT.add(role);
                    case "SERVICE_WORKER" -> SERVICE_WORKER.add(role);
                    default -> throw new IllegalStateException("There should never be value other than the prior 3");
                }
            }
        }
        for(var permission : permissionModelMap.values()) {
            for(var accountType : permission.getAccountsThatHaveItByDefault()) {
                switch (accountType) {
                    case "USER" -> USER.add(permission);
                    case "ROOT" -> ROOT.add(permission);
                    case "SERVICE_WORKER" -> SERVICE_WORKER.add(permission);
                    default -> throw new IllegalStateException("There should never be value other than the prior 3");
                }
            }
        }

        defaultAuthoritiesOf_USER = ImmutableList.copyOf(USER);
        defaultAuthoritiesOf_ROOT = ImmutableList.copyOf(ROOT);
        defaultAuthoritiesOf_SERVICE_WORKER = ImmutableList.copyOf(SERVICE_WORKER);
    }

    private static void checkIntegrityOfAuthorities() {
        for (var roleModel : roleModelMap.values()) {
            for (var canBeGivenBy : roleModel.mayBeGivenBy()) {
                if (!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                        && !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Role: " + roleModel.getAuthorityName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }

        for (var permissionModel : permissionModelMap.values()) {
            for (var canBeGivenBy : permissionModel.mayBeGivenBy()) {
                if (!permissionModelMap.containsKey(canBeGivenBy.getAuthority())
                        && !roleModelMap.containsKey(canBeGivenBy.getAuthority())) {
                    throw new IllegalStateException("Permission: " + permissionModel.getAuthorityName() +
                            " have property canBeGivenBy assigned to non existed authority:" +
                            canBeGivenBy.getAuthority());
                }
            }
        }
    }

}

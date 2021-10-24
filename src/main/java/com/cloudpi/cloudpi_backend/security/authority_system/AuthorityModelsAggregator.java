package com.cloudpi.cloudpi_backend.security.authority_system;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
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
    private static final ImmutableList<AuthorityDTO> defaultAuthoritiesOf_USER;
    private static final ImmutableList<AuthorityDTO> defaultAuthoritiesOf_ROOT;
    private static final ImmutableList<AuthorityDTO> defaultAuthoritiesOf_SERVICE_WORKER;



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

    public static Collection<AuthorityDTO> getDefaultAuthorities(String accountType) {
        return switch (accountType) {
            case AccountType.user -> defaultAuthoritiesOf_USER;
            case AccountType.root -> defaultAuthoritiesOf_ROOT;
            case AccountType.serviceWorker ->  defaultAuthoritiesOf_SERVICE_WORKER;
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
        List<AuthorityDTO> authoritiesOfUSER = new ArrayList<>();
        List<AuthorityDTO> authoritiesOfROOT = new ArrayList<>();
        List<AuthorityDTO> authoritiesOfSERVICE_WORKER = new ArrayList<>();

        for(var role : roleModelMap.values()) {
            for(var accountType : role.getAccountsThatHaveItByDefault()) {
                switch (accountType) {
                    case "USER" -> authoritiesOfUSER.add(role.toAuthorityDTO());
                    case "ROOT" -> authoritiesOfROOT.add(role.toAuthorityDTO());
                    case "SERVICE_WORKER" -> authoritiesOfSERVICE_WORKER.add(role.toAuthorityDTO());
                    default -> throw new IllegalStateException("There should never be value other than the prior 3");
                }
            }
        }
        for(var permission : permissionModelMap.values()) {
            for(var accountType : permission.getAccountsThatHaveItByDefault()) {
                switch (accountType) {
                    case "USER" -> authoritiesOfUSER.add(permission.toAuthorityDTO());
                    case "ROOT" -> authoritiesOfROOT.add(permission.toAuthorityDTO());
                    case "SERVICE_WORKER" -> authoritiesOfSERVICE_WORKER.add(permission.toAuthorityDTO());
                    default -> throw new IllegalStateException("There should never be value other than the prior 3");
                }
            }
        }

        defaultAuthoritiesOf_USER = ImmutableList.copyOf(authoritiesOfUSER);
        defaultAuthoritiesOf_ROOT = ImmutableList.copyOf(authoritiesOfROOT);
        defaultAuthoritiesOf_SERVICE_WORKER = ImmutableList.copyOf(authoritiesOfSERVICE_WORKER);
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

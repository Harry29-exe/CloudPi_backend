package com.cloudpi.cloudpi_backend.security.authority_system;

import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsPermissions;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.ContainsRoles;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Permission;
import com.cloudpi.cloudpi_backend.security.authority_system.annotations.Role;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import lombok.AllArgsConstructor;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class AuthorityModelScanner {
    private Map<String, PermissionModel> permissionModels = new HashMap<>();
    private Map<String, RoleModel> roleModels = new HashMap<>();
    private Map<String, RolePrototype> rolePrototypes = new HashMap<>();
    private Map<String, PermissionPrototype> permissionPrototypes = new HashMap<>();


    public AuthorityModelScanner() {
        try {
            createPermissionsPrototypes();
            createRolePrototypes();
            mapPermissionPrototypesToModels();
            mapRolePrototypesToModels();

        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Something very bad happened");
        }
    }

    public ImmutableSortedMap<String, PermissionModel> getPermissionModels() {
        return ImmutableSortedMap.copyOf(permissionModels);
    }

    public ImmutableSortedMap<String, RoleModel> getRoleModels() {
        return ImmutableSortedMap.copyOf(roleModels);
    }

    private void mapRolePrototypesToModels() {
        for(var rolePrototype : rolePrototypes.values()) {

            RoleModel model = new SimpleRoleModel(
                    rolePrototype.roleName,
                    ImmutableSortedSet.copyOf(rolePrototype.permissions.stream()
                            .map(prototype -> permissionModels.get(prototype.permissionName))
                            .collect(Collectors.toList())),
                    ImmutableSortedSet.copyOf(rolePrototype.mayBeGivenByArray),
                    ImmutableSortedSet.copyOf(rolePrototype.haveItByDefault)
            );
            roleModels.put(model.getAuthorityName(), model);
        }
    }

    private void createRolePrototypes() throws IllegalAccessException {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var roleClasses = reflections.getTypesAnnotatedWith(ContainsRoles.class);

        Map<String, Class<?>> duplicationCheckMap = new HashMap<>();

        for (var roleClass : roleClasses) {
            var fields = getAllRoleFields(roleClass);
            for (var field : fields) {
                String roleName = (String) field.get(null);
                var fieldAnnotation = field.getAnnotation(Role.class);

                if (duplicationCheckMap.containsKey(roleName)) {
                    throw new IllegalStateException(
                            "Permission with name: " + roleName +
                                    "exist in class: " + duplicationCheckMap.get(roleName).getName() +
                                    " and  in class: " + roleClass.getName());
                }
                duplicationCheckMap.put(roleName, roleClass);

                var mayBeGivenBy = new ArrayList<>(Arrays.asList(fieldAnnotation.mayBeGivenBy()));
                if(!mayBeGivenBy.contains(roleName)) {
                    mayBeGivenBy.add(roleName);
                }
                rolePrototypes.put(roleName, new RolePrototype(
                        roleName,
                        Arrays.stream(fieldAnnotation.permissions()).map(permissionPrototypes::get).toList(),
                        mayBeGivenBy,
                        List.of(fieldAnnotation.havingItByDefault()),
                        fieldAnnotation.authorityOwnersCanShareIt()
                ));
            }
        }
    }




    private List<Field> getAllRoleFields(Class<?> roleClass) {
        LinkedList<Field> fields = new LinkedList<>();

        for (var field : roleClass.getFields()) {
            int fieldModifiers = field.getModifiers();

            if (field.isAnnotationPresent(Role.class) && String.class.isAssignableFrom(field.getType())
                    && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers)
            ) {
                fields.addLast(field);
            }
        }

        return fields;
    }

    private void mapPermissionPrototypesToModels() {
        for(var prototype : permissionPrototypes.values()) {
            var model = new SimplePermissionModel(
                    prototype.permissionName,
                    ImmutableSortedSet.copyOf(prototype.collectMayBeGivenBy()),
                    ImmutableSortedSet.copyOf(prototype.haveItByDefault));
            permissionModels.put(model.getAuthorityName(), model);
        }
    }

    private void createPermissionsPrototypes() throws IllegalAccessException {
        var reflections = new Reflections("com.cloudpi.cloudpi_backend");
        var permissionClasses = reflections.getTypesAnnotatedWith(ContainsPermissions.class);

        Map<String, Class<?>> duplicatesCheckMap = new HashMap<>();

        for (var permissionClass : permissionClasses) {
            var fields = getAllPermissionFields(permissionClass);
            for (var field : fields) {
                var permissionName = (String) field.get(null);
                var permissionAnnotation = field.getAnnotation(Permission.class);

                if (duplicatesCheckMap.containsKey(permissionName)) {
                    throw new IllegalStateException(
                            "Permission with name: " + permissionName +
                                    "exist in class: " + duplicatesCheckMap.get(permissionName).getName() +
                                    " and  in class: " + permissionClass.getName());
                }
                duplicatesCheckMap.put(permissionName, permissionClass);


                var mayBeGivenBy = Arrays.stream(permissionAnnotation.mayBeGivenBy()).toList();

                permissionPrototypes.put(permissionName, new PermissionPrototype(
                        permissionName,
                        permissionAnnotation.mayBeGivenBy(),
                        List.of(permissionAnnotation.havingItByDefault()),
                        permissionAnnotation.authorityOwnersCanShareIt()
                ));
            }
        }
    }

    private List<Field> getAllPermissionFields(Class<?> permissionClass) {
        LinkedList<Field> fields = new LinkedList<>();

        for (var field : permissionClass.getFields()) {
            int fieldModifiers = field.getModifiers();

            if (field.isAnnotationPresent(Permission.class) && String.class.isAssignableFrom(field.getType())
                    && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers)
            ) {
                fields.addLast(field);
            }
        }

        return fields;
    }

    private List<String> getMayBeGivenBy(Role role, String roleName) {
        var mayBeGivenBy = Arrays.asList(role.mayBeGivenBy());
        if (role.authorityOwnersCanShareIt() && !mayBeGivenBy.contains(roleName)) {
            mayBeGivenBy.add(roleName);
        }

        return mayBeGivenBy;
    }

    @AllArgsConstructor
    private class RolePrototype {
        String roleName;
        List<PermissionPrototype> permissions;
        List<String> mayBeGivenByArray;
        List<String> haveItByDefault;
        boolean authorityOwnersCanShareIt;
    }

    @AllArgsConstructor
    class PermissionPrototype {
        String permissionName;
        String[] mayBeGivenByArray;
        List<String> haveItByDefault;
        boolean authorityOwnersCanShareIt;

        public List<String> collectMayBeGivenBy() {
            if(!authorityOwnersCanShareIt) {
                return Arrays.asList(mayBeGivenByArray);
            }

            HashSet<String> mayBeGivenBy = new HashSet<>(Arrays.asList(mayBeGivenByArray));
            for (var prototype : rolePrototypes.values()) {
                prototype.permissions.stream()
                        .filter(pp -> pp.permissionName.equals(permissionName))
                        .forEach(pp -> mayBeGivenBy.add(pp.permissionName));
            }
            mayBeGivenBy.add(permissionName);

            return mayBeGivenBy.stream().toList();
        }
    }
}
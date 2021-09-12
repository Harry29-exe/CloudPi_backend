package com.cloudpi.cloudpi_backend.user.entities;

import com.cloudpi.cloudpi_backend.authorization.dto.AccountType;
import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityPermissionEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.cloudpi.cloudpi_backend.user.UserTestUtils.createDefaultUserDTO;

class UserEntityTest {


//    @Test
//    void should_successfully_map_entity_to_dto() {
//        var mapper = UserMapper.INSTANCE;
//        var userEntity = new UserEntity(0L, "username", "email",
//                "nickname", "password", false, AccountType.USER,
//                new ArrayList<>(), null, null);
//
//        userEntity.getPermissions().add(
//                new AuthorityPermissionEntity(0L, "permission", userEntity)
//        );
//        var dto = mapper.userEntityToUserDTO(userEntity);
//        assert dto.getPermissions().get(0).getAuthority().equals(
//                userEntity.getPermissions().get(0).getAuthority()
//        );
//    }
//
//    @Test
//    void should_successfully_map_dto_to_entity() {
//        var mapper = UserMapper.INSTANCE;
//        var dto = createDefaultUserDTO();
//        dto.setPermissions(List.of(new SimpleGrantedAuthority("authority")));
//
//        var entity = mapper.userDTOToUserEntity(dto);
//        assert dto.getPermissions().get(0).getAuthority().equals(entity.getPermissions().get(0).getAuthority());
//    }

}
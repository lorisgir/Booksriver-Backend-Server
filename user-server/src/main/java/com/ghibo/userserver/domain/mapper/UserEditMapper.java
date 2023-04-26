package com.ghibo.userserver.domain.mapper;

import com.ghibo.userserver.domain.dto.requests.CreateUserRequest;
import com.ghibo.userserver.domain.dto.requests.UpdateUserRequest;
import com.ghibo.userserver.domain.models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserEditMapper {

    User create(CreateUserRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(UpdateUserRequest request, @MappingTarget User user);
}

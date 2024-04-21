package com.computercomponent.api.mapper;

import com.computercomponent.api.dto.auth.*;
import com.computercomponent.api.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AdminMapper extends EntityMapper<AdminDto, Admin>{
    Admin map(UserRegistrationDto userRegistrationDto);

    Admin reflect(@MappingTarget Admin admin, UserBasicInfo userBasicInfo);

    Admin reflect(@MappingTarget Admin admin, UserUpdateByAdmin userUpdateByAdmin);


    List<AdminDto> map(List<Admin> admins);

    UserProfileDto map(Admin principal);
}

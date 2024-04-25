package com.computercomponent.api.mapper;

import com.computercomponent.api.dto.auth.AdminDto;
import com.computercomponent.api.dto.auth.UserBasicInfo;
import com.computercomponent.api.dto.auth.UserProfileDto;
import com.computercomponent.api.dto.auth.UserRegistrationDto;
import com.computercomponent.api.dto.auth.UserUpdateByAdmin;
import com.computercomponent.api.entity.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-25T20:46:27+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.22 (Amazon.com Inc.)"
)
public class AdminMapperImpl implements AdminMapper {

    @Override
    public Admin toEntity(AdminDto dto) {
        if ( dto == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setUsername( dto.getUsername() );
        admin.setEmail( dto.getEmail() );
        admin.setStatus( dto.getStatus() );
        admin.setMobile( dto.getMobile() );

        return admin;
    }

    @Override
    public AdminDto toDto(Admin entity) {
        if ( entity == null ) {
            return null;
        }

        AdminDto adminDto = new AdminDto();

        adminDto.setEmail( entity.getEmail() );
        adminDto.setMobile( entity.getMobile() );
        adminDto.setUsername( entity.getUsername() );
        adminDto.setStatus( entity.getStatus() );

        return adminDto;
    }

    @Override
    public List<Admin> toEntity(List<AdminDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Admin> list = new ArrayList<Admin>( dtoList.size() );
        for ( AdminDto adminDto : dtoList ) {
            list.add( toEntity( adminDto ) );
        }

        return list;
    }

    @Override
    public List<AdminDto> toDto(List<Admin> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AdminDto> list = new ArrayList<AdminDto>( entityList.size() );
        for ( Admin admin : entityList ) {
            list.add( toDto( admin ) );
        }

        return list;
    }

    @Override
    public void toEntity(AdminDto dto, Admin entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getMobile() != null ) {
            entity.setMobile( dto.getMobile() );
        }
    }

    @Override
    public void toDto(Admin entity, AdminDto dto) {
        if ( entity == null ) {
            return;
        }

        if ( entity.getEmail() != null ) {
            dto.setEmail( entity.getEmail() );
        }
        if ( entity.getMobile() != null ) {
            dto.setMobile( entity.getMobile() );
        }
        if ( entity.getUsername() != null ) {
            dto.setUsername( entity.getUsername() );
        }
        if ( entity.getStatus() != null ) {
            dto.setStatus( entity.getStatus() );
        }
    }

    @Override
    public Admin map(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setPassword( userRegistrationDto.getPassword() );
        admin.setEmail( userRegistrationDto.getEmail() );
        admin.setMobile( userRegistrationDto.getMobile() );

        return admin;
    }

    @Override
    public Admin reflect(Admin admin, UserBasicInfo userBasicInfo) {
        if ( userBasicInfo == null ) {
            return admin;
        }

        if ( userBasicInfo.getEmail() != null ) {
            admin.setEmail( userBasicInfo.getEmail() );
        }
        if ( userBasicInfo.getMobile() != null ) {
            admin.setMobile( userBasicInfo.getMobile() );
        }

        return admin;
    }

    @Override
    public Admin reflect(Admin admin, UserUpdateByAdmin userUpdateByAdmin) {
        if ( userUpdateByAdmin == null ) {
            return admin;
        }

        if ( userUpdateByAdmin.getEmail() != null ) {
            admin.setEmail( userUpdateByAdmin.getEmail() );
        }
        if ( userUpdateByAdmin.getMobile() != null ) {
            admin.setMobile( userUpdateByAdmin.getMobile() );
        }

        return admin;
    }

    @Override
    public List<AdminDto> map(List<Admin> admins) {
        if ( admins == null ) {
            return null;
        }

        List<AdminDto> list = new ArrayList<AdminDto>( admins.size() );
        for ( Admin admin : admins ) {
            list.add( toDto( admin ) );
        }

        return list;
    }

    @Override
    public UserProfileDto map(Admin principal) {
        if ( principal == null ) {
            return null;
        }

        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setUsername( principal.getUsername() );
        userProfileDto.setPassword( principal.getPassword() );
        userProfileDto.setEmail( principal.getEmail() );
        userProfileDto.setMobile( principal.getMobile() );
        userProfileDto.setStatus( principal.getStatus() );

        return userProfileDto;
    }
}

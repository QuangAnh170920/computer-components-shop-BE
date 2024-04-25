package com.computercomponent.api.repository;

import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    Optional<Admin> findOneByEmailIgnoreCaseAndDeletedAndStatus(String email, Boolean deleted, UserStatus userStatus);

    Optional<Admin> findOneByMobileIgnoreCaseAndDeletedAndStatus(String mobile, Boolean deleted, UserStatus userStatus);

    Optional<Admin> findOneByEmailIgnoreCaseAndDeleted(String email, Boolean deleted);

    Optional<Admin> findFirstByMobileAndDeleted(String mobile, Boolean deleted);

    Optional<Admin> findFirstByMobileAndDeletedAndStatus(String mobile, Boolean deleted, UserStatus userStatus);

    Optional<Admin> findByAdminIdAndDeletedAndStatus(Long adminId, Boolean deleted, UserStatus status);
}

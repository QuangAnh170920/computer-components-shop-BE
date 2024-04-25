package com.computercomponent.api.repository;

import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    Optional<Admin> findOneByEmailIgnoreCaseAndDeletedAndStatus(String email, int deleted, UserStatus userStatus);

    Optional<Admin> findOneByMobileIgnoreCaseAndDeletedAndStatus(String mobile, int deleted, UserStatus userStatus);

    Optional<Admin> findOneByEmailIgnoreCaseAndDeleted(String email, int deleted);

    Optional<Admin> findFirstByMobileAndDeleted(String mobile, int deleted);

    Optional<Admin> findFirstByMobileAndDeletedAndStatus(String mobile, int deleted, UserStatus userStatus);

    Optional<Admin> findByAdminIdAndDeletedAndStatus(Long adminId, int deleted, UserStatus status);
}

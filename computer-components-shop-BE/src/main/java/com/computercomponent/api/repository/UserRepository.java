package com.computercomponent.api.repository;

import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.entity.Admin;
import com.computercomponent.api.entity.Orders;
import com.computercomponent.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findOneByMobileIgnoreCaseAndDeletedAndStatus(String mobile, boolean deleted, UserStatus userStatus);

    Optional<User> findOneByEmailIgnoreCaseAndDeletedAndStatus(String email, boolean deleted, UserStatus userStatus);

    Optional<User> findOneByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

    Optional<User> findFirstByMobileAndDeleted(String mobile, boolean deleted);

    Optional<User> findFirstByMobileAndDeletedAndStatus(String mobile, boolean deleted, UserStatus userStatus);

    @Query(value = "select u from User u  where u.id = :id and u.deleted = false")
    User findUserById(Long id);

    Optional<User> findByIdAndDeletedAndStatus(Long id, Boolean deleted, UserStatus status);

    boolean existsByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

    boolean existsByMobileAndDeleted(String mobile, boolean deleted);

    @Query("select count(u) > 0 from User u where (u.email = :email or u.mobile = :mobile) and u.deleted = false")
    boolean existsByEmailIgnoreCaseOrMobile(String email, String mobile);
}

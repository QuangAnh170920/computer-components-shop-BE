package com.computercomponent.api.repository;

import com.computercomponent.api.common.UserStatus;
import com.computercomponent.api.entity.Orders;
import com.computercomponent.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findOneByMobileIgnoreCaseAndDeletedAndStatus(String mobile, boolean deleted, UserStatus userStatus);

    Optional<User> findOneByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

    Optional<User> findFirstByMobileAndDeleted(String mobile, boolean deleted);

    Optional<User> findFirstByMobileAndDeletedAndStatus(String mobile, boolean deleted, UserStatus userStatus);

    @Query(value = "select u from User u  where u.id = :id and u.deleted = false")
    User findUserById(Long id);
}

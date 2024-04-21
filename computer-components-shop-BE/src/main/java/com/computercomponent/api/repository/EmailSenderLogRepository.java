package com.computercomponent.api.repository;

import com.computercomponent.api.entity.EmailSenderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSenderLogRepository extends JpaRepository<EmailSenderLog, Long> {
}

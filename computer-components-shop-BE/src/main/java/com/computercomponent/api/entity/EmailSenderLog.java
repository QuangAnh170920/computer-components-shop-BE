package com.computercomponent.api.entity;

import com.computercomponent.api.common.EmailStatus;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "email_sender_log")
@EntityListeners(AuditingEntityListener.class)
@Data
public class EmailSenderLog {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    private String sendTo;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private Date sendAt;

    @Column(columnDefinition = "TEXT")
    private String errorLog;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String emailContent;

    private String sendBccTo;

    private String sendCcTo;

    private String sendFrom;

}

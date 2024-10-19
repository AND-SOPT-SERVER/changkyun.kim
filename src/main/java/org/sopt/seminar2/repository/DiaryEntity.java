package org.sopt.seminar2.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    protected DiaryEntity() {
    }

    private DiaryEntity(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public static DiaryEntity create(final String title, final String body) {
        return new DiaryEntity(title, body);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
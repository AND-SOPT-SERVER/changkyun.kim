package org.sopt.seminar2.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.sopt.seminar2.common.enums.Category;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "diary_entity")
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected DiaryEntity() {
    }

    private DiaryEntity(Category category, String title, String body) {
        this.category = category;
        this.title = title;
        this.body = body;
    }

    public static DiaryEntity create(final Category category, final String title, final String body) {
        return new DiaryEntity(category, title, body);
    }

    public void editBody(final String newBody) {
        this.body = newBody;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

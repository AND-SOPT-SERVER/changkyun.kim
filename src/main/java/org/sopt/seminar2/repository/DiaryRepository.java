package org.sopt.seminar2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findTop10ByOrderByCreateAtDesc();
}

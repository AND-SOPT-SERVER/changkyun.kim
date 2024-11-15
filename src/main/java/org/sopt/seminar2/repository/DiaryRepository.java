package org.sopt.seminar2.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.seminar2.common.enums.Category;
import org.sopt.seminar2.common.enums.ErrorType;
import org.sopt.seminar2.common.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    boolean existsByTitle(final String title);

    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.body) DESC")
    List<DiaryEntity> findTop10ByOrderByBodyLengthDesc();

    List<DiaryEntity> findAllByCategory(final Category category);

    Optional<DiaryEntity> findDiaryEntityById(final Long id);

    default DiaryEntity findDiaryEntityByIdOrThrow(final Long id) {
        return findDiaryEntityById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_DIARY_ERROR));
    }
}

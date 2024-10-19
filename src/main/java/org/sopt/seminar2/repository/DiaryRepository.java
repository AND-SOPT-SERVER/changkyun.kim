package org.sopt.seminar2.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.seminar2.common.enums.ErrorType;
import org.sopt.seminar2.common.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findTop10ByOrderByCreateAtDesc();

    Optional<DiaryEntity> findDiaryEntityById(final Long id);

    default DiaryEntity findDiaryEntityByIdOrThrow(final Long id) {
        return findDiaryEntityById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_DIARY_ERROR));
    }
}

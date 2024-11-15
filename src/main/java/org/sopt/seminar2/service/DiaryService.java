package org.sopt.seminar2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.sopt.seminar2.api.DiaryDetailResponse;
import org.sopt.seminar2.api.DiaryResponse;
import org.sopt.seminar2.common.enums.Category;
import org.sopt.seminar2.common.enums.ErrorType;
import org.sopt.seminar2.common.enums.OrderBy;
import org.sopt.seminar2.common.exception.CustomException;
import org.sopt.seminar2.repository.DiaryEntity;
import org.sopt.seminar2.repository.DiaryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final EntityManager entityManager;

    public DiaryService(DiaryRepository diaryRepository, EntityManager entityManager) {
        this.diaryRepository = diaryRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public void writeDiary(final Category category, final String title, final String body) {
        if (diaryRepository.existsByTitle(title)) {
            throw new CustomException(ErrorType.DUPLICATE_DIARY_TITLE_ERROR);
        }

        final DiaryEntity newDiaryEntity = DiaryEntity.create(category, title, body);
        diaryRepository.save(newDiaryEntity);
    }

    @Transactional(readOnly = true)
    public List<DiaryResponse> fetchDiaryList(final OrderBy orderBy) {
        final List<DiaryEntity> diaryEntityList = findDiaryEntitiesByOrderCriteria(orderBy);

        List<DiaryResponse> list = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            DiaryResponse diaryResponse = new DiaryResponse(diaryEntity.getId(), diaryEntity.getTitle());
            list.add(diaryResponse);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<DiaryResponse> fetchDiaryList(final Category category) {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findAllByCategory(category);

        List<DiaryResponse> list = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            DiaryResponse diaryResponse = new DiaryResponse(diaryEntity.getId(), diaryEntity.getTitle());
            list.add(diaryResponse);
        }

        return list;
    }

    private List<DiaryEntity> findDiaryEntitiesByOrderCriteria(final OrderBy orderBy) {
        return switch (orderBy) {
            case CREATED_AT_DESC -> diaryRepository.findTop10ByOrderByCreatedAtDesc();
            case BODY_LENGTH_DESC -> diaryRepository.findTop10ByOrderByBodyLengthDesc();
        };
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse fetchDiaryDetail(final Long id) {
        final DiaryEntity diaryEntity = diaryRepository.findDiaryEntityByIdOrThrow(id);

        return new DiaryDetailResponse(
                diaryEntity.getId(),
                diaryEntity.getCategory(),
                diaryEntity.getTitle(),
                diaryEntity.getBody(),
                diaryEntity.getCreatedAt()
        );
    }

    @Transactional
    public void editDiary(final Long id, final String newBody) {
        DiaryEntity diaryEntity = diaryRepository.findDiaryEntityByIdOrThrow(id);
        diaryEntity.editBody(newBody);
    }

    @Transactional
    public void removeDiary(final Long id) {
        Query query = entityManager.createNativeQuery("DELETE FROM diary_entity WHERE id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}

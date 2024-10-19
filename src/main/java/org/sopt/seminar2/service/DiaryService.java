package org.sopt.seminar2.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.sopt.seminar2.api.DiaryDetailResponse;
import org.sopt.seminar2.api.DiaryRequest;
import org.sopt.seminar2.api.DiaryResponse;
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
    public void writeDiary(final DiaryRequest diaryRequest) {
        final DiaryEntity newDiaryEntity = DiaryEntity.create(diaryRequest.title(), diaryRequest.body());
        diaryRepository.save(newDiaryEntity);
    }

    @Transactional(readOnly = true)
    public List<DiaryResponse> getDiaryList() {
        final List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByCreateAtDesc();

        List<DiaryResponse> list = new ArrayList<>();

        for (DiaryEntity diaryEntity : diaryEntityList) {
            DiaryResponse diaryResponse = new DiaryResponse(diaryEntity.getId(), diaryEntity.getTitle());
            list.add(diaryResponse);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse getDiaryDetail(final Long id) {
        final DiaryEntity diaryEntity = diaryRepository.findDiaryEntityByIdOrThrow(id);

        return new DiaryDetailResponse(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getBody(),
                diaryEntity.getCreateAt()
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

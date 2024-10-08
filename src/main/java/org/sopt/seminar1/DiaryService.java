package org.sopt.seminar1;

import java.util.List;
import org.sopt.seminar1.Main.UI.InvalidInputException;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body) {
        final Diary diary = new Diary(null, body);

        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    void deleteDiary(final Long id) {
        if (diaryRepository.existsById(id)) {
            diaryRepository.delete(id);
        } else {
            throw new InvalidInputException();
        }
    }
}

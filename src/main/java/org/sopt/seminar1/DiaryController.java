package org.sopt.seminar1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        Validator.validateLength(body);

        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        Validator.validateId(id);

        diaryService.deleteDiary(Long.valueOf(id));
    }

    final void patch(final String id, final String body) {
        Validator.validateId(id);

        diaryService.editDiary(Long.valueOf(id), body);
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}

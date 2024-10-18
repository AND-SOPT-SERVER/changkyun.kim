package org.sopt.seminar2.api;

import java.util.List;
import org.sopt.seminar2.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diaries")
    ResponseEntity<Void> postDiary(
            @RequestBody DiaryRequest diaryRequest
    ) {
        diaryService.writeDiary(diaryRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/diaries")
    ResponseEntity<List<DiaryResponse>> getDiaryList() {
        return ResponseEntity.ok(diaryService.getDiaryList());
    }
}

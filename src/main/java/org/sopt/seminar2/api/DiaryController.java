package org.sopt.seminar2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.sopt.seminar2.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/diaries")
@Validated
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    ResponseEntity<Void> postDiary(
            @Valid @RequestBody final DiaryRequest diaryRequest
    ) {
        diaryService.writeDiary(diaryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    ResponseEntity<List<DiaryResponse>> getDiaryList() {
        return ResponseEntity.ok(diaryService.getDiaryList());
    }

    @GetMapping("/{id}")
    ResponseEntity<DiaryDetailResponse> getDiaryDetail(
            @NotNull(message = "일기 Id는 공백일 수 없습니다.")
            @Positive(message = "일기 Id는 양수여야 합니다.")
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(diaryService.getDiaryDetail(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> patchDiary(
            @NotNull(message = "일기 Id는 공백일 수 없습니다.")
            @Positive(message = "일기 Id는 양수여야 합니다.")
            @PathVariable final Long id,
            @Valid @RequestBody final PatchDiaryRequest patchDiaryRequest
    ) {
        diaryService.editDiary(id, patchDiaryRequest.newBody());

        return ResponseEntity.ok().build();
    }
}

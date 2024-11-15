package org.sopt.seminar2.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.sopt.seminar2.common.enums.Category;
import org.sopt.seminar2.common.enums.OrderBy;
import org.sopt.seminar2.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @Valid @RequestBody final PostDiaryRequest postDiaryRequest
    ) {
        Category category = postDiaryRequest.convertToCategory();
        diaryService.writeDiary(category, postDiaryRequest.title(), postDiaryRequest.body());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    ResponseEntity<List<DiaryResponse>> getDiaryList(
            @RequestParam final String order
    ) {
        OrderBy parsedOrderBy = OrderBy.fromValue(order);

        return ResponseEntity.ok(diaryService.fetchDiaryList(parsedOrderBy));
    }

    @GetMapping("/by-category")
    ResponseEntity<List<DiaryResponse>> getDiaryListByCategory(
            @RequestParam final String category
    ) {
        Category parsedCategory = Category.fromValue(category);

        return ResponseEntity.ok(diaryService.fetchDiaryList(parsedCategory));
    }

    @GetMapping("/{id}")
    ResponseEntity<DiaryDetailResponse> getDiaryDetail(
            @Positive(message = "일기 Id는 양수여야 합니다.")
            @PathVariable final Long id
    ) {
        return ResponseEntity.ok(diaryService.fetchDiaryDetail(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> patchDiary(
            @Positive(message = "일기 Id는 양수여야 합니다.")
            @PathVariable final Long id,
            @Valid @RequestBody final PatchDiaryRequest patchDiaryRequest
    ) {
        diaryService.editDiary(id, patchDiaryRequest.newBody());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteDiary(
            @Positive(message = "일기 Id는 양수여야 합니다.")
            @PathVariable final Long id
    ) {
        diaryService.removeDiary(id);

        return ResponseEntity.ok().build();
    }
}

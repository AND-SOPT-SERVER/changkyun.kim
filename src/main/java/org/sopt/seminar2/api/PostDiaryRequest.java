package org.sopt.seminar2.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.sopt.seminar2.common.enums.Category;

public record PostDiaryRequest(
        @NotBlank(message = "카테고리는 공백일 수 없습니다.")
        String category,
        @NotBlank(message = "일기 제목은 공백일 수 없습니다.")
        String title,
        @NotBlank(message = "일기 내용은 공백일 수 없습니다.")
        @Size(max = 30, message = "일기 내용은 최대 30자까지 입력 가능합니다.")
        String body
) {
    public Category convertToCategory() {
        return Category.fromValue(category);
    }
}

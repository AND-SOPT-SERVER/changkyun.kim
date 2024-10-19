package org.sopt.seminar2.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiaryRequest(
        @NotBlank(message = "일기 제목은 공백일 수 없습니다.")
        String title,
        @NotBlank(message = "일기 내용은 공백일 수 없습니다.")
        @Size(max = 30, message = "일기 내용은 최대 30자까지 입력 가능합니다.")
        String body
) {
}

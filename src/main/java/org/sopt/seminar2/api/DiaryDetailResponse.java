package org.sopt.seminar2.api;

import java.time.LocalDateTime;

public record DiaryDetailResponse(
        Long id,
        String title,
        String body,
        LocalDateTime createAt
) {
}

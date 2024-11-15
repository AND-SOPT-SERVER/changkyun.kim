package org.sopt.seminar2.api;

import java.time.LocalDateTime;
import org.sopt.seminar2.common.enums.Category;

public record DiaryDetailResponse(
        Long id,
        Category category,
        String title,
        String body,
        LocalDateTime createAt
) {
}

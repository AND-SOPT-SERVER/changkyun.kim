package org.sopt.seminar2.api;

import jakarta.validation.constraints.NotBlank;
import org.sopt.seminar2.common.enums.OrderBy;

public record DiaryListRequest(
        @NotBlank(message = "정렬 기준은 공백일 수 없습니다.")
        String orderBy
) {
    public OrderBy convertToOrderBy() {
        return OrderBy.fromValue(orderBy);
    }
}

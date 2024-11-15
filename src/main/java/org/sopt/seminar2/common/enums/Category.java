package org.sopt.seminar2.common.enums;

import java.util.HashMap;
import java.util.Map;
import org.sopt.seminar2.common.exception.CustomException;

public enum Category {

    MEALS("MEALS"),
    WORKOUT("WORKOUT"),

    ;

    private final String type;
    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            CATEGORY_MAP.put(category.getType(), category);
        }
    }

    private Category(String type) {
        this.type = type;
    }

    public static Category fromValue(String value) {
        Category category = CATEGORY_MAP.get(value);

        if (category == null) {
            throw new CustomException(ErrorType.INVALID_CATEGORY_TYPE_ERROR);
        }

        return category;
    }

    public String getType() {
        return type;
    }
}

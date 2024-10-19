package org.sopt.seminar2.common.enums;

import java.util.HashMap;
import java.util.Map;
import org.sopt.seminar2.common.exception.CustomException;

public enum OrderBy {

    CREATED_AT_DESC("CREATED_AT_DESC"),
    BODY_LENGTH_DESC("BODY_LENGTH_DESC"),

    ;

    private final String orderCriteria;
    private static final Map<String, OrderBy> ORDER_BY_MAP = new HashMap<>();

    static {
        for (OrderBy orderBy : OrderBy.values()) {
            ORDER_BY_MAP.put(orderBy.getOrderCriteria(), orderBy);
        }
    }

    private OrderBy(String orderCriteria) {
        this.orderCriteria = orderCriteria;
    }

    public static OrderBy fromValue(String value) {
        OrderBy orderBy = ORDER_BY_MAP.get(value);

        if (orderBy == null) {
            throw new CustomException(ErrorType.INVALID_ORDER_CRITERIA_ERROR);
        }
        return orderBy;
    }

    public String getOrderCriteria() {
        return orderCriteria;
    }
}
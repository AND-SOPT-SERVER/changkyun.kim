package org.sopt.seminar2.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "40001", "잘못된 요청입니다."),
    DATA_INTEGRITY_VIOLATION_ERROR(HttpStatus.BAD_REQUEST, "40002", "데이터 무결성 제약 조건을 위반했습니다."),
    NOT_FOUND_DIARY_ERROR(HttpStatus.NOT_FOUND, "40401", "id에 해당하는 일기가 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50001", "예상치 못한 서버 오류가 발생했습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    private ErrorType(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
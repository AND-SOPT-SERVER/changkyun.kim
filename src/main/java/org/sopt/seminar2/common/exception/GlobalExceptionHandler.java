package org.sopt.seminar2.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.seminar2.common.dto.ResponseDto;
import org.sopt.seminar2.common.enums.ErrorType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 비즈니스 로직에서 발생하는 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<?>> handleCustomException(CustomException e) {
        logger.error("비즈니스 로직 예외 발생: {}", e.getErrorType().getMessage());
        return ResponseEntity
                .status(e.getErrorType().getHttpStatus())
                .body(ResponseDto.fail(e.getErrorType()));
    }

    // @Validated 에서 발생하는 유효성 검사 예외 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error("@Validated 유효성 검사 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INVALID_REQUEST_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INVALID_REQUEST_ERROR, e.getConstraintViolations()));
    }

    // @Valid 에서 발생하는 유효성 검사 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("@Valid 유효성 검사 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INVALID_REQUEST_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INVALID_REQUEST_ERROR, e.getBindingResult()));
    }

    // 데이터 무결성 위반 예외 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logger.error("데이터 무결성 제약 조건 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.DATA_INTEGRITY_VIOLATION_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.DATA_INTEGRITY_VIOLATION_ERROR));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleGeneralException(Exception e) {
        logger.error("알 수 없는 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}

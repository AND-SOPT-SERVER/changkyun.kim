package org.sopt.seminar2.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.seminar2.common.dto.ResponseDto;
import org.sopt.seminar2.common.enums.ErrorType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    // @Validated 유효성 검사 시 예외 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error("@Validated 유효성 검사 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INVALID_REQUEST_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INVALID_REQUEST_ERROR, e.getConstraintViolations()));
    }

    // @Valid 유효성 검사 시 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("@Valid 유효성 검사 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INVALID_REQUEST_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INVALID_REQUEST_ERROR, e.getBindingResult()));
    }

    // 컨트롤러 메서드에 전달된 값의 타입 변환 시 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto<?>> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("컨트롤러 메서드 타입 변환 예외 발생: {}", e.getMessage());
        String errorDetail = String.format("'%s'는 %s 타입이어야 합니다.", e.getValue(), e.getRequiredType().getSimpleName());
        return ResponseEntity
                .status(ErrorType.TYPE_MISMATCH_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.TYPE_MISMATCH_ERROR, errorDetail));
    }

    // 잘못된 Request Body로 인해 발생하는 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("Request Body 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INVALID_REQUEST_BODY_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INVALID_REQUEST_BODY_ERROR));
    }

    // 데이터 무결성 위반 시 예외 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        logger.error("데이터 무결성 위반 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.DATA_INTEGRITY_VIOLATION_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.DATA_INTEGRITY_VIOLATION_ERROR));
    }

    // 기타 에러 발생 시 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleGeneralException(Exception e) {
        logger.error("알 수 없는 예외 발생: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ResponseDto.fail(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}

package org.sopt.seminar2.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import org.sopt.seminar2.common.enums.ErrorType;
import org.springframework.validation.BindingResult;

@JsonInclude(Include.NON_NULL)
public record ResponseDto<T>(
        String code,
        T data,
        String message,
        Object errors
) {
    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>("success", data, null, null);
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage(), null);
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final String errorDetail) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage() + " (" + errorDetail + ")", null);
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final BindingResult bindingResult) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage(), ValidationError.of(bindingResult));
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final Set<ConstraintViolation<?>> violations) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage(), ValidationError.of(violations));
    }

    private record ValidationError(
            String field,
            String message
    ) {
        // BindingResult에서 FieldError 목록을 ValidationError로 변환
        private static List<ValidationError> of(final BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                    .toList();
        }

        // ConstraintViolation 목록을 ValidationError로 변환
        private static List<ValidationError> of(final Set<ConstraintViolation<?>> violations) {
            return violations.stream()
                    .map(violation -> new ValidationError(violation.getPropertyPath().toString(),
                            violation.getMessage()))
                    .toList();
        }
    }
}

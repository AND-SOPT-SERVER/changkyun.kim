package org.sopt.seminar2.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import org.sopt.seminar2.common.enums.ErrorType;
import org.springframework.validation.BindingResult;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final String detail) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage() + " (" + detail + ")", null);
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final BindingResult bindingResult) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage(), ValidationError.of(bindingResult));
    }

    public static <T> ResponseDto<T> fail(final ErrorType errorType, final Set<ConstraintViolation<?>> violations) {
        return new ResponseDto<>(errorType.getCode(), null, errorType.getMessage(), ValidationError.of(violations));
    }

    public static class ValidationError {
        private final String field;
        private final String message;

        private ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        // BindingResult에서 FieldError 목록을 ValidationError로 변환
        public static List<ValidationError> of(final BindingResult bindingResult) {
            return bindingResult.getFieldErrors().stream()
                    .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                    .toList();
        }

        // ConstraintViolation 목록을 ValidationError로 변환
        public static List<ValidationError> of(final Set<ConstraintViolation<?>> violations) {
            return violations.stream()
                    .map(violation -> new ValidationError(violation.getPropertyPath().toString(),
                            violation.getMessage()))
                    .toList();
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}

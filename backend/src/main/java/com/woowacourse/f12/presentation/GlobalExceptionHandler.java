package com.woowacourse.f12.presentation;

import static com.woowacourse.f12.exception.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.woowacourse.f12.exception.ErrorCode.INVALID_REQUEST_BODY_TYPE;
import static com.woowacourse.f12.exception.ErrorCode.INVALID_SEARCH_PARAM;

import com.woowacourse.f12.dto.response.ExceptionResponse;
import com.woowacourse.f12.exception.CustomException;
import com.woowacourse.f12.exception.badrequest.InvalidValueException;
import com.woowacourse.f12.exception.forbidden.ForbiddenMemberException;
import com.woowacourse.f12.exception.internalserver.ExternalServerException;
import com.woowacourse.f12.exception.internalserver.InternalServerException;
import com.woowacourse.f12.exception.notfound.NotFoundException;
import com.woowacourse.f12.exception.unauthorized.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String REQUEST_DATA_FORMAT_ERROR_MESSAGE = "요청으로 넘어온 값이 형식에 맞지 않습니다.";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 오류가 발생했습니다";
    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(final NotFoundException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidValueException(final InvalidValueException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), INVALID_REQUEST_BODY_TYPE.getValue(), e.getMessage());
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.from(REQUEST_DATA_FORMAT_ERROR_MESSAGE, INVALID_REQUEST_BODY_TYPE));
    }

    @ExceptionHandler({BindException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ExceptionResponse> handleInvalidQueryParameterException(Exception e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), INVALID_SEARCH_PARAM.getValue(), e.getMessage());
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.from(REQUEST_DATA_FORMAT_ERROR_MESSAGE, INVALID_SEARCH_PARAM));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            final MethodArgumentNotValidException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), INVALID_REQUEST_BODY_TYPE.getValue(), e.getMessage());
        final StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> stringBuilder.append(error.getDefaultMessage())
                .append(System.lineSeparator()));
        return ResponseEntity.badRequest()
                .body(ExceptionResponse.from(stringBuilder.toString(), INVALID_REQUEST_BODY_TYPE));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(final UnauthorizedException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(ForbiddenMemberException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenMemberException(final ForbiddenMemberException e) {
        log.info(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode().getValue(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionResponse.from(e));
    }

    @ExceptionHandler({ExternalServerException.class, InternalServerException.class})
    public ResponseEntity<ExceptionResponse> handleInternalException(final CustomException e) {
        return ResponseEntity.internalServerError().body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnhandledException(final Exception e) {
        e.printStackTrace();
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), INTERNAL_SERVER_ERROR.getValue(), e.getMessage());
        return ResponseEntity.internalServerError()
                .body(ExceptionResponse.from(INTERNAL_SERVER_ERROR_MESSAGE, INTERNAL_SERVER_ERROR));
    }
}

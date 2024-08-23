package com.redcare_pharmacy.popular_git_repos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GithubRepositoriesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({GitHubApiException.class})
    public ResponseEntity<ErrorResponseDto> handleGitHubApiException(GitHubApiException exception) {

        final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status(internalServerError.getReasonPhrase())
                .statusCode(internalServerError.value())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponseDto, internalServerError);
    }
}

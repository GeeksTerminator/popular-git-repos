package com.redcare_pharmacy.popular_git_repos.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDto {

    private LocalDateTime timestamp;
    private String status;
    @JsonProperty("status_code")
    private int statusCode;
    private String message;
}

package com.redcare_pharmacy.popular_git_repos.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositorySearchFilter {

    @Schema(description = "Programming language filter", implementation = String.class)
    @NotEmpty(message = "Language cannot be empty nor null")
    private String language;

    @Schema(description = "Earliest creation date filter in yyyy-MM-dd format")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "EarliestCreationDate cannot be null")
    private LocalDate earliestCreationDate;
}

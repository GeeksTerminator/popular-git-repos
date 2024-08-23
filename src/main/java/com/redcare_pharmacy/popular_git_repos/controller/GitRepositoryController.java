package com.redcare_pharmacy.popular_git_repos.controller;

import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import com.redcare_pharmacy.popular_git_repos.service.GitRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.redcare_pharmacy.popular_git_repos.config.ApplicationConstants.API_ROOT;
import static com.redcare_pharmacy.popular_git_repos.config.ApplicationConstants.GIT_REPOSITORIES_API_PATH;

@RestController
@RequestMapping(API_ROOT + GIT_REPOSITORIES_API_PATH)
@RequiredArgsConstructor
public class GitRepositoryController {

    private final GitRepositoryService service;

    @Operation(summary = "Search Git repositories by given params, score them by popularity and return them")
    @GetMapping("/popular")
    public ResponseEntity<List<RepositoryDto>> searchPopularGitRepositories(@ParameterObject @Valid RepositorySearchFilter filter) {

        List<RepositoryDto> repositories = service.searchPopularRepositories(filter);
        return ResponseEntity.ok(repositories);
    }
}

package com.redcare_pharmacy.popular_git_repos.service;

import com.redcare_pharmacy.popular_git_repos.client.GitHubClient;
import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import com.redcare_pharmacy.popular_git_repos.exception.GitHubApiException;
import com.redcare_pharmacy.popular_git_repos.mapper.GitHubRepositoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

/**
 * Service class responsible for fetching and sorting GitHub repositories based on popularity. Also perform other related operations if possible
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GitHubRepositoryService implements GitRepositoryService {

    private final GitHubClient githubClient;
    private final GitHubRepositoryMapper mapper;

    /**
     * Fetches GitHub repositories based on the provided filter and sorts them by popularity.
     * <p>
     * The popularity of a repository is determined using the following criteria in this order:
     * <ol>
     *     <li><strong>Stars:</strong> Repositories with more stars are considered more popular.</li>
     *     <li><strong>Forks:</strong> If two repositories have the same star count, the number of forks is used as a tiebreaker.</li>
     *     <li><strong>Recency of Updates:</strong> If two repositories have the same stars and forks, the one updated more recently is considered more popular.</li>
     * </ol>
     * </p>
     * <p>
     * The sorting is performed in descending order, meaning that the most popular repositories will appear first in the result.
     * This method relies on {@link GitHubClient} to perform the API call and {@link GitHubRepositoryMapper}
     * to map the API response into DTOs used in the application.
     * </p>
     *
     * @param filter The search filter containing criteria for searching repositories on GitHub, such as language and earliest creation date. Must not be null.
     * @return A list of {@link RepositoryDto} objects sorted by popularity in descending order.
     * @throws NullPointerException if the filter is null.
     * @throws GitHubApiException   if the {@link GitHubClient} failed to call the GitHub search API.
     */
    @Override
    public List<RepositoryDto> searchPopularRepositories(final RepositorySearchFilter filter) {

        requireNonNull(filter, "Filter cannot be null");
        List<GitHubRepositoryDto> repositories = githubClient.searchRepositories(filter);

        log.debug("Scoring and sorting [{}] git repositories", repositories.stream());
        return repositories.stream()
                .map(mapper::toRepositoryDto)
                .sorted(comparing(RepositoryDto::getStars)
                        .thenComparing(RepositoryDto::getForks)
                        .thenComparing(RepositoryDto::getUpdatedAt)
                        .reversed()
                )
                .toList();
    }
}

package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubSearchApiResponse;
import com.redcare_pharmacy.popular_git_repos.client.dto.SortOrder;
import com.redcare_pharmacy.popular_git_repos.client.dto.SortParam;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import com.redcare_pharmacy.popular_git_repos.exception.GitHubApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Client class for interacting with the GitHub API to fetch repositories.
 * It simplifies integration with the GitHub API by handling the query construction and response parsing.
 * Additionally, it uses {@link RestTemplate} for GitHub integration
 */
@Slf4j
@Component
public class GitHubClientImpl implements GitHubClient {

    private final String githubSearchApiUrl;
    private final String githubRepositorySearchLimit;
    private final GithubSearchQueryBuilder githubSearchQueryBuilder;
    private final RestTemplate restTemplate;

    public GitHubClientImpl(@Value("${github.api.base.url}${github.api.search.path}") String githubSearchApiUrl,
                            @Value("${github.client.repositories.search.limit}") String githubRepositorySearchLimit,
                            GithubSearchQueryBuilder githubSearchQueryBuilder,
                            RestTemplate restTemplate) {
        this.githubSearchApiUrl = githubSearchApiUrl;
        this.githubRepositorySearchLimit = githubRepositorySearchLimit;
        this.githubSearchQueryBuilder = githubSearchQueryBuilder;
        this.restTemplate = restTemplate;
    }

    /**
     * Searches for repositories on GitHub based on the provided filter.
     * <p>
     * The search request is optimized to retrieve repositories sorted by stars in descending order.
     * This helps mitigate the time complexity of further sorting/scoring processes within the application by providing a dataset
     * that is already partially sorted by one of the key popularity factors (stars).
     * </p>
     *
     * @param filter The filter criteria for searching repositories. Must not be null.
     * @return A list of {@link GitHubRepositoryDto} containing repository details matching the search criteria.
     * @throws NullPointerException if the filter is null or the API response body is null.
     * @throws GitHubApiException   in case of HTTP error.
     */
    @Override
    public List<GitHubRepositoryDto> searchRepositories(RepositorySearchFilter filter) {

        requireNonNull(filter, "Filter cannot be null");

        String url = UriComponentsBuilder.fromHttpUrl(githubSearchApiUrl)
                .queryParam("q", githubSearchQueryBuilder.buildByFilter(filter))
                .queryParam("per_page", githubRepositorySearchLimit)
                .queryParam("sort", SortParam.STARS.getValue())
                .queryParam("order", SortOrder.DESC.getValue())
                .build()
                .toUriString();

        log.debug("Searching repositories: {}", url);
        ResponseEntity<GitHubSearchApiResponse> response = restTemplate.getForEntity(url, GitHubSearchApiResponse.class);

        if (response.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            // Handle the 304 response gracefully. can be enhanced later to fetch cached data for the respective request.
            return Collections.emptyList();
        }

        return requireNonNull(response.getBody(), "GithubApi: Response body is null").getItems();
    }
}

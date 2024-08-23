package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubSearchApiResponse;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GitHubClientImplTest {

    public static final String GITHUB_DUMMY_SEARCH_API_URL = "https://api.dummy.com/search/repositories";
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private GithubSearchQueryBuilder queryBuilder;
    private GitHubClientImpl underTest;

    @Captor
    private ArgumentCaptor<String> urlCaptor;

    @BeforeEach
    void setUp() {

        this.underTest = new GitHubClientImpl(
                GITHUB_DUMMY_SEARCH_API_URL, "100",
                queryBuilder,
                restTemplate
        );
    }

    @Test
    void givenNullFilter_whenSearchRepositories_thenThrowNullPointerException() {

        NullPointerException thrownException = catchThrowableOfType(() -> underTest.searchRepositories(null), NullPointerException.class);
        assertThat(thrownException).hasMessage("Filter cannot be null");
    }

    @Test
    void givenValidFilter_whenSearchRepositories_thenReturnNullResponseBodyAndThrowNullPointerException() {

        // Assign
        RepositorySearchFilter filter = RepositorySearchFilter.builder()
                .language("Java")
                .earliestCreationDate(LocalDate.now())
                .build();

        // Mock
        String expectedGitHubSearchQuery = "language:Java+created:>=2024-01-01";
        when(queryBuilder.buildByFilter(filter))
                .thenReturn(expectedGitHubSearchQuery);

        ResponseEntity<GitHubSearchApiResponse> expectedResponseEntity = Mockito.mock(ResponseEntity.class);
        when(expectedResponseEntity.getBody()).thenReturn(null);
        when(restTemplate.getForEntity(urlCaptor.capture(), any(Class.class))).thenReturn(expectedResponseEntity);

        // Act & Assert
        NullPointerException thrownException = catchThrowableOfType(() -> underTest.searchRepositories(filter), NullPointerException.class);
        assertThat(thrownException).hasMessage("GithubApi: Response body is null");
        assertThat(urlCaptor.getValue()).isEqualTo(GITHUB_DUMMY_SEARCH_API_URL + "?q=" + expectedGitHubSearchQuery + "&per_page=100&sort=stars&order=desc");
    }

    @Test
    void givenValidFilter_whenSearchRepositories_thenReturnRepositoriesAsExpected() {

        // Assign
        RepositorySearchFilter filter = RepositorySearchFilter.builder()
                .language("Java")
                .earliestCreationDate(LocalDate.now())
                .build();

        // Mock
        String expectedGitHubSearchQuery = "language:Java+created:>=2024-01-01";
        when(queryBuilder.buildByFilter(filter))
                .thenReturn(expectedGitHubSearchQuery);

        ResponseEntity<GitHubSearchApiResponse> expectedResponseEntity = Mockito.mock(ResponseEntity.class);
        GitHubSearchApiResponse gitHubSearchApiResponse = GitHubSearchApiResponse.builder()
                .items(List.of(GitHubRepositoryDto.builder().stars(10).forks(5).updatedAt(LocalDateTime.now()).build()))
                .build();

        when(expectedResponseEntity.getBody()).thenReturn(gitHubSearchApiResponse);
        when(restTemplate.getForEntity(urlCaptor.capture(), any(Class.class))).thenReturn(expectedResponseEntity);

        // Act
        List<GitHubRepositoryDto> result = underTest.searchRepositories(filter);

        // Assert
        assertThat(result).hasSameElementsAs(gitHubSearchApiResponse.getItems());
        assertThat(urlCaptor.getValue()).isEqualTo(GITHUB_DUMMY_SEARCH_API_URL + "?q=" + expectedGitHubSearchQuery + "&per_page=100&sort=stars&order=desc");
    }
}
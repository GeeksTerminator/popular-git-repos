package com.redcare_pharmacy.popular_git_repos.service;

import com.redcare_pharmacy.popular_git_repos.client.GitHubClient;
import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import com.redcare_pharmacy.popular_git_repos.mapper.GitHubRepositoryMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubRepositoryServiceTest {

    @Mock
    private GitHubClient githubClient;
    @Mock
    private GitHubRepositoryMapper mapper;
    @InjectMocks
    private GitHubRepositoryService underTest;

    @Test
    void givenNullFilter_whenSearchPopularRepositories_thenThrowNullPointerException() {

        NullPointerException thrownException = catchThrowableOfType(() -> underTest.searchPopularRepositories(null), NullPointerException.class);
        assertThat(thrownException).hasMessage("Filter cannot be null");

        verifyNoInteractions(githubClient);
        verifyNoInteractions(mapper);
    }

    @Test
    void givenValidFilter_whenSearchPopularRepositories_thenReturnEmptyListWhenHubAPIResponseIsEmpty() {

        RepositorySearchFilter filter = RepositorySearchFilter.builder().build();
        when(githubClient.searchRepositories(filter)).thenReturn(Collections.emptyList());

        List<RepositoryDto> sortedRepositories = underTest.searchPopularRepositories(filter);

        Assertions.assertThat(sortedRepositories).isEmpty();
        verify(githubClient).searchRepositories(filter);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void givenValidFilter_whenSearchPopularRepositories_thenReturnScoredGitRepositoriesAsExpected() {

        RepositorySearchFilter filter = RepositorySearchFilter.builder().build();

        LocalDateTime updatedAt1 = LocalDateTime.now().minusMinutes(3);
        LocalDateTime updatedAt2 = LocalDateTime.now().minusMinutes(5);
        LocalDateTime updatedAt3 = LocalDateTime.now().minusMinutes(2);
        LocalDateTime updatedAt4 = LocalDateTime.now().minusMinutes(1);
        GitHubRepositoryDto repo1 = GitHubRepositoryDto.builder().name("Repo1").stars(50).forks(30).updatedAt(updatedAt1).build();
        GitHubRepositoryDto repo2 = GitHubRepositoryDto.builder().name("Repo2").stars(70).forks(20).updatedAt(updatedAt2).build();
        GitHubRepositoryDto repo3 = GitHubRepositoryDto.builder().name("Repo3").stars(50).forks(40).updatedAt(updatedAt3).build();
        GitHubRepositoryDto repo4 = GitHubRepositoryDto.builder().name("Repo4").stars(50).forks(40).updatedAt(updatedAt4).build();

        when(githubClient.searchRepositories(filter)).thenReturn(List.of(repo1, repo2, repo3, repo4));

        RepositoryDto dto1 = RepositoryDto.builder().name("Repo1").stars(50).forks(30).updatedAt(updatedAt1).build();
        RepositoryDto dto2 = RepositoryDto.builder().name("Repo2").stars(70).forks(20).updatedAt(updatedAt2).build();
        RepositoryDto dto3 = RepositoryDto.builder().name("Repo3").stars(50).forks(40).updatedAt(updatedAt3).build();
        RepositoryDto dto4 = RepositoryDto.builder().name("Repo4").stars(50).forks(40).updatedAt(updatedAt4).build();

        when(mapper.toRepositoryDto(repo1)).thenReturn(dto1);
        when(mapper.toRepositoryDto(repo2)).thenReturn(dto2);
        when(mapper.toRepositoryDto(repo3)).thenReturn(dto3);
        when(mapper.toRepositoryDto(repo4)).thenReturn(dto4);

        List<RepositoryDto> sortedRepositories = underTest.searchPopularRepositories(filter);

        Assertions.assertThat(sortedRepositories).containsExactly(dto2, dto4, dto3, dto1);  // Sorted by stars, then forks, then updatedAt
        verify(githubClient).searchRepositories(filter);
        verify(mapper, Mockito.times(4)).toRepositoryDto(any(GitHubRepositoryDto.class));
    }
}
package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;

import java.util.List;

public interface GitHubClient {

    List<GitHubRepositoryDto> searchRepositories(RepositorySearchFilter filter);
}

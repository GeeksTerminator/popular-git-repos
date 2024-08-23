package com.redcare_pharmacy.popular_git_repos.service;

import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;

import java.util.List;

public interface GitRepositoryService {

    List<RepositoryDto> searchPopularRepositories(RepositorySearchFilter filter);
}

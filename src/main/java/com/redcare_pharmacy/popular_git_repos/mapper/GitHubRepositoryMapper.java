package com.redcare_pharmacy.popular_git_repos.mapper;

import com.redcare_pharmacy.popular_git_repos.client.dto.GitHubRepositoryDto;
import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitHubRepositoryMapper {

    RepositoryDto toRepositoryDto(GitHubRepositoryDto gitHubRepositoryDto);
}

package com.redcare_pharmacy.popular_git_repos.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryDto {

    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("stargazers_count")
    private int stars;
    @JsonProperty("forks_count")
    private int forks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private GithubOwnerDto owner;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    private String homepage;
    @JsonProperty("watchers_count")
    private int watchersCount;
    private String language;
    @JsonProperty("open_issues_count")
    private int openIssuesCount;
}

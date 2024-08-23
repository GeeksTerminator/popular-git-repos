package com.redcare_pharmacy.popular_git_repos.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepositoryDto {

    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private OwnerDto owner;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private LocalDateTime updatedAt;
    private String homepage;
    private int stars;
    @JsonProperty("watchers_count")
    private int watchersCount;
    private String language;
    private int forks;
    @JsonProperty("open_issues_count")
    private int openIssuesCount;
}

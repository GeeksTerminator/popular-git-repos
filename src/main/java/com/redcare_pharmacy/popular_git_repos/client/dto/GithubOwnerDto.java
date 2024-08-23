package com.redcare_pharmacy.popular_git_repos.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubOwnerDto {

    private String login;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String url;
}

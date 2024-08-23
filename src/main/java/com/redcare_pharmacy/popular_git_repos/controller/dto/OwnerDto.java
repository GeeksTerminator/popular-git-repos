package com.redcare_pharmacy.popular_git_repos.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OwnerDto {

    private String login;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String url;
}
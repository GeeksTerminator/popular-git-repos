package com.redcare_pharmacy.popular_git_repos.client.dto;

import lombok.Getter;

@Getter
public enum GithubSearchQualifier {

    PROGRAMMING_LANGUAGE("language:"),
    EARLIEST_CREATION_DATE("created:>=");

    private final String value;

    GithubSearchQualifier(String value) {
        this.value = value;
    }
}

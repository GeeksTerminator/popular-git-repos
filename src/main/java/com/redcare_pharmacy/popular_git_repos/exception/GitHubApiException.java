package com.redcare_pharmacy.popular_git_repos.exception;

public class GitHubApiException extends RuntimeException {

    public GitHubApiException(String message) {
        super("GitHubApi:" + message);
    }
}

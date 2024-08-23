package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.exception.GitHubApiException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

public class GithubApiErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        switch (response.getStatusCode()) {
            case UNPROCESSABLE_ENTITY:
                throw new GitHubApiException("Validation failed or endpoint has been spammed");
            case SERVICE_UNAVAILABLE:
                throw new GitHubApiException("GitHub service is temporarily unavailable. Please try again later");
            default:
                throw new GitHubApiException("Unexpected response[" + response.getStatusText() + " - " + response.getStatusCode() + "]");
        }
    }
}

package com.redcare_pharmacy.popular_git_repos.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.redcare_pharmacy.popular_git_repos.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.redcare_pharmacy.popular_git_repos.config.ApplicationConstants.API_ROOT;
import static com.redcare_pharmacy.popular_git_repos.config.ApplicationConstants.GIT_REPOSITORIES_API_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@EnableWireMock({
        @ConfigureWireMock(name = "github-service", property = "github.api.base.url", port = 8080, stubLocation = "mappings")
})
class GitRepositoryControllerITest extends BaseIntegrationTest {

    @Test
    void givenFilter_whenSearchPopularGitRepositories_thenReturnPopularRepositories() throws Exception {
        final String earliestCreationDate = "2024-08-01";
        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/search/repositories\\?q=language:javascript\\+created:%3E%3D" + earliestCreationDate + "&per_page=100&sort=stars&order=desc"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader("Content-Type", "application/json")
                                        .withBodyFile("github-search-repositories-OK-response.json")
                        )
        );

        mockMvc.perform(get(API_ROOT + GIT_REPOSITORIES_API_PATH + "/popular?language=javascript&earliestCreationDate=" + earliestCreationDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("jCat"))
                .andExpect(jsonPath("$[1].name").value("Chunker"))
                .andExpect(jsonPath("$[2].name").value("ddpoker"))
                .andExpect(jsonPath("$[3].name").value("Mnemonics-Seeds-Gen"));
    }

    @Test
    void givenFilter_whenSearchPopularGitRepositories_thenHandleUnprocessableEntityError() throws Exception {
        final String earliestCreationDate = "2024-08-02";
        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/search/repositories\\?q=language:javascript\\+created:%3E%3D" + earliestCreationDate + "&per_page=100&sort=stars&order=desc"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        )
        );

        mockMvc.perform(get(API_ROOT + GIT_REPOSITORIES_API_PATH + "/popular?language=javascript&earliestCreationDate=" + earliestCreationDate))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("GitHubApi:Validation failed or endpoint has been spammed"));
    }

    @Test
    void givenFilter_whenSearchPopularGitRepositories_thenHandleServiceUnavailableError() throws Exception {
        // Simulate 503 Service Unavailable response
        final String earliestCreationDate = "2024-08-03";
        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/search/repositories\\?q=language:javascript\\+created:%3E%3D" + earliestCreationDate + "&per_page=100&sort=stars&order=desc"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                        )
        );

        mockMvc.perform(get(API_ROOT + GIT_REPOSITORIES_API_PATH + "/popular?language=javascript&earliestCreationDate=" + earliestCreationDate))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("GitHubApi:GitHub service is temporarily unavailable. Please try again later"));
    }

    @Test
    void givenFilter_whenSearchPopularGitRepositories_thenHandleNotModifiedError() throws Exception {

        final String earliestCreationDate = "2024-08-04";
        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/search/repositories\\?q=language:javascript\\+created:%3E%3D" + earliestCreationDate + "&per_page=100&sort=stars&order=desc"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.NOT_MODIFIED.value())
                        )
        );

        mockMvc.perform(get(API_ROOT + GIT_REPOSITORIES_API_PATH + "/popular?language=javascript&earliestCreationDate=" + earliestCreationDate))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void givenFilter_whenSearchPopularGitRepositories_thenHandleUnexpectedError() throws Exception {
        // Simulate an unexpected HTTP status

        final String earliestCreationDate = "2024-08-05";
        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/search/repositories\\?q=language:javascript\\+created:%3E%3D" + earliestCreationDate + "&per_page=100&sort=stars&order=desc"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(HttpStatus.BAD_GATEWAY.value())
                        )
        );

        mockMvc.perform(get(API_ROOT + GIT_REPOSITORIES_API_PATH + "/popular?language=javascript&earliestCreationDate=" + earliestCreationDate))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("GitHubApi:Unexpected response[Bad Gateway - 502 BAD_GATEWAY]"));
    }
}

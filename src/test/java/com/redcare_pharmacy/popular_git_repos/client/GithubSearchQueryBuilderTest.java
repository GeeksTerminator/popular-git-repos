package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class GithubSearchQueryBuilderTest {

    private GithubSearchQueryBuilder underTest;

    @BeforeEach
    void setUp() {

        this.underTest = new GithubSearchQueryBuilder();
    }

    @Test
    void givenNullFilter_whenBuildByFilter_thenReturnNull() {

        String query = underTest.buildByFilter(null);
        Assertions.assertThat(query).isNull();
    }

    @Test
    void givenPartialFilter_whenBuildByFilter_thenMatchQuery() {

        String query1 = underTest.buildByFilter(RepositorySearchFilter.builder().language("Java").build());
        Assertions.assertThat(query1).isEqualTo("language:Java");

        String query2 = underTest.buildByFilter(
                RepositorySearchFilter.builder()
                        .language(" ")
                        .earliestCreationDate(LocalDate.of(2024, 1, 1))
                        .build()
        );
        Assertions.assertThat(query2).isEqualTo("created:>=2024-01-01");
    }

    @Test
    void givenFullFilter_whenBuildByFilter_thenMatchQuery() {

        String query = underTest.buildByFilter(
                RepositorySearchFilter.builder()
                        .language("Java")
                        .earliestCreationDate(LocalDate.of(2024, 1, 1))
                        .build()
        );
        Assertions.assertThat(query).isEqualTo("language:Java+created:>=2024-01-01");
    }
}
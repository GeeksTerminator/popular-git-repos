package com.redcare_pharmacy.popular_git_repos.client;

import com.redcare_pharmacy.popular_git_repos.controller.dto.RepositorySearchFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.redcare_pharmacy.popular_git_repos.client.dto.GithubSearchQualifier.EARLIEST_CREATION_DATE;
import static com.redcare_pharmacy.popular_git_repos.client.dto.GithubSearchQualifier.PROGRAMMING_LANGUAGE;

@Component
@Slf4j
public class GithubSearchQueryBuilder {

    public String buildByFilter(RepositorySearchFilter filter) {

        if (Objects.isNull(filter)) {
            return null;
        }

        final StringBuilder queryBuilder = new StringBuilder();

        if (Objects.nonNull(filter.getLanguage()) && !filter.getLanguage().trim().isEmpty()) {
            queryBuilder.append(PROGRAMMING_LANGUAGE.getValue()).append(filter.getLanguage()).append("+");
        }

        if (Objects.nonNull(filter.getEarliestCreationDate())) {
            queryBuilder.append(EARLIEST_CREATION_DATE.getValue()).append(filter.getEarliestCreationDate());
        }

        final String query = queryBuilder.toString().replaceAll("\\+$", "");
        log.debug("Used search query: {}", query);

        return query;
    }
}

package com.redcare_pharmacy.popular_git_repos.client.dto;

import lombok.Getter;

@Getter
public enum SortOrder {

	ASC("asc"),
	DESC("desc");

	private final String value;

	SortOrder(String value) {
		this.value = value;
	}
}
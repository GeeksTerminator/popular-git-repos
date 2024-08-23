package com.redcare_pharmacy.popular_git_repos.client.dto;

import lombok.Getter;

@Getter
public enum SortParam {

	STARS("stars");

	private final String value;

	SortParam(String value) {
		this.value = value;
	}
}
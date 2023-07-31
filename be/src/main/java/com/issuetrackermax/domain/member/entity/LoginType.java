package com.issuetrackermax.domain.member.entity;

import lombok.Getter;

@Getter
public enum LoginType {
	LOCAL("LOCAL"), GITHUB("GITHUB");

	private String name;

	LoginType(String name) {
		this.name = name;
	}

}

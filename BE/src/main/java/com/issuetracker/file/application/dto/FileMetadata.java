package com.issuetracker.file.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileMetadata {

	private String fileName;
	private String url;

	public static FileMetadata from(String fileName, String url) {
		return new FileMetadata(fileName, url);
	}
}

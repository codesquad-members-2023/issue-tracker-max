package com.issuetracker.file.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileInformation {

	private String url;

	public static FileInformation from(String fileName, String s3Url) {
		return new FileInformation(String.format("![%s](%s)", fileName, s3Url));
	}
}

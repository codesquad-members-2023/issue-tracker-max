package com.issuetracker.file.ui.dto;

import com.issuetracker.file.application.dto.FileInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class FileResponse {

	private String url;

	public static FileResponse from(FileInformation fileInformation) {
		return new FileResponse(fileInformation.getUrl());
	}
}

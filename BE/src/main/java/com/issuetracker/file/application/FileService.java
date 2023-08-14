package com.issuetracker.file.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.file.application.dto.FileMetadata;
import com.issuetracker.file.doamin.ImageFileDetail;
import com.issuetracker.file.doamin.FileUpload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileUpload fileUpload;

	public FileMetadata upload(MultipartFile multipartFile) {
		ImageFileDetail imageFileDetail = new ImageFileDetail(multipartFile);
		String uploadUrl = fileUpload.upload(imageFileDetail);
		return FileMetadata.from(imageFileDetail.getFileName(), uploadUrl);
	}
}

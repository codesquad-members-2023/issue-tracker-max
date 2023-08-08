package com.issuetracker.file.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.file.application.dto.FileInformation;
import com.issuetracker.file.doamin.ImageFileDetail;
import com.issuetracker.file.doamin.FileUpload;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileUpload fileUpload;

	public FileInformation upload(MultipartFile multipartFile) {
		ImageFileDetail imageFileDetail = new ImageFileDetail(multipartFile);
		String uploadUrl = fileUpload.upload(imageFileDetail);
		return FileInformation.from(imageFileDetail.getFileName(), uploadUrl);
	}
}

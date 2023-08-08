package com.issuetracker.file.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.file.application.FileService;
import com.issuetracker.file.ui.dto.FileResponse;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/images")
@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping
	public ResponseEntity<FileResponse> upload(@RequestPart(value = "image") MultipartFile multipartFile) {
		FileResponse fileResponse = FileResponse.from(fileService.upload(multipartFile));
		return ResponseEntity.ok().body(fileResponse);
	}
}

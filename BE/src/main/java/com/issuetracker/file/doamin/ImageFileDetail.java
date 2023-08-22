package com.issuetracker.file.doamin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;

public class ImageFileDetail {

	private static final List<String> IMAGES = List.of(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);

	private final MultipartFile multipartFile;

	public ImageFileDetail(MultipartFile multipartFile) {
		validateImageFile(multipartFile.getContentType());
		this.multipartFile = multipartFile;
	}

	private void validateImageFile(String contentType) {
		if (!IMAGES.contains(contentType)) {
			throw new CustomHttpException(ErrorType.FILE_CONTENT_TYPE);
		}
	}

	public InputStream getInputStream() {
		try {
			return multipartFile.getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getContentType() {
		return multipartFile.getContentType();
	}

	public long getContentLength() {
		return multipartFile.getSize();
	}

	public String getUploadFileName(String prefix) {
		return String.format("%s/%s", prefix, UUID.randomUUID());
	}

	public String getFileName() {
		return multipartFile.getOriginalFilename();
	}
}

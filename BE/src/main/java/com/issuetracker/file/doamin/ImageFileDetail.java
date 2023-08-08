package com.issuetracker.file.doamin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;

public class ImageFileDetail {

	private static final List<String> IMAGES = List.of(MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);

	private final MultipartFile multipartFile;
	private final File file;

	public ImageFileDetail(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
		this.file = convertFile();
	}

	private File convertFile() {
		validateImageFile(multipartFile.getContentType());
		File convertFile = new File(multipartFile.getOriginalFilename());

		try (FileOutputStream fos = new FileOutputStream(convertFile)) {
			fos.write(multipartFile.getBytes());
		} catch (IOException e) {
			throw new CustomHttpException(ErrorType.FILE_NOT_FOUND);
		}

		return convertFile;
	}

	private void validateImageFile(String contentType) {
		if (!IMAGES.contains(contentType)) {
			throw new CustomHttpException(ErrorType.FILE_CONTENT_TYPE);
		}
	}

	public String getUploadFileName(String prefix) {
		return String.format("%s/%s", prefix, UUID.randomUUID());
	}

	public String getFileName() {
		return multipartFile.getOriginalFilename();
	}

	public File getFile() {
		return file;
	}

	public void removeFile() {
		file.delete();
	}
}

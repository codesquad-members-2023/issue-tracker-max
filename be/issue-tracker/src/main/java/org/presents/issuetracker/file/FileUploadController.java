package org.presents.issuetracker.file;

import java.io.IOException;
import java.util.Map;

import org.presents.issuetracker.global.error.exception.CustomException;
import org.presents.issuetracker.global.error.statuscode.FileErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@PostMapping("/file-upload")
	public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(file.getContentType());
			objectMetadata.setContentLength(file.getSize());

			amazonS3Client.putObject(bucket, fileName, file.getInputStream(), objectMetadata);
			return ResponseEntity.ok().body(Map.of("fileUrl", amazonS3Client.getUrl(bucket, fileName).toString()));
		} catch (IOException e) {
			throw new CustomException(FileErrorCode.FILE_UPLOAD_FAIL);
		}
	}
}

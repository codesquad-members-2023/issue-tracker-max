package com.issuetrackermax.service.comment;

import static com.issuetrackermax.common.util.CommonUtil.*;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.S3Exception;
import com.issuetrackermax.domain.comment.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private static final String FILE_EXTENSION_SEPARATOR = ".";
	private static final String TIME_SEPARATOR = "_";

	private final AmazonS3Client amazonS3Client;
	private final CommentRepository commentRepository;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	// private void validateFileExists(MultipartFile multipartFile) {
	// 	if (multipartFile.isEmpty()) {
	// 		throw new EmptyFileException();
	// 	}

	// todo : 예외처리 구체화
	public String uploadFile(MultipartFile multipartFile) {
		String fileName = buildFileName(multipartFile.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new ApiException(S3Exception.IMAGE_SIZE_EXCEEDED);
		}

		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}

}

package codesquard.app.image;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import codesquard.app.errors.exception.EmptyFileException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String upload(MultipartFile multipartFile) throws IOException {
		validateFileExists(multipartFile);

		String fileName = multipartFile.getOriginalFilename() + "-" + UUID.randomUUID();
		ObjectMetadata objectMetadata = generateObjectMetadata(multipartFile);
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
			.withCannedAcl(CannedAccessControlList.PublicRead));

		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new EmptyFileException();
		}
	}

	private static ObjectMetadata generateObjectMetadata(MultipartFile multipartFile) throws IOException {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getInputStream().available());
		return objectMetadata;
	}

}

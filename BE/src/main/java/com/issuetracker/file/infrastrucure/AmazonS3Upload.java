package com.issuetracker.file.infrastrucure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.issuetracker.file.doamin.ImageFileDetail;
import com.issuetracker.file.doamin.FileUpload;

@Component
public class AmazonS3Upload implements FileUpload {

	private static final String BUCKET_FOLDER = "images";

	private final String bucketName;
	private final AmazonS3 amazonS3;

	public AmazonS3Upload(@Value("${cloud.aws.s3.bucket}") String bucketName, AmazonS3 amazonS3) {
		this.bucketName = bucketName;
		this.amazonS3 = amazonS3;
	}

	@Override
	public String upload(ImageFileDetail imageFileDetail) {
		String s3Key = imageFileDetail.getUploadFileName(BUCKET_FOLDER);
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Key, imageFileDetail.getFile())
			.withCannedAcl(CannedAccessControlList.PublicRead);

		amazonS3.putObject(putObjectRequest);
		imageFileDetail.removeFile();
		return amazonS3.getUrl(bucketName, s3Key).toString();
	}
}

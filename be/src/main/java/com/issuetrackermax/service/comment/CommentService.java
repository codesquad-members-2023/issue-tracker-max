package com.issuetrackermax.service.comment;

import java.util.List;

import static com.issuetrackermax.common.util.CommonUtil.*;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;
import com.issuetrackermax.controller.comment.dto.response.CommentResponse;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.S3Exception;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.CommentValidator;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.member.MemberValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final MemberValidator memberValidator;
	private final CommentValidator commentValidator;
	private static final String FILE_EXTENSION_SEPARATOR = ".";
	private static final String TIME_SEPARATOR = "_";

	private final AmazonS3Client amazonS3Client;
	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public List<Comment> findByIssueId(Long id) {
		return commentRepository.findByIssueId(id);
	}

	@Transactional
	public void modifyComment(CommentModifyRequest commentModifyRequest, Long commentId, Long memberId) {
		memberValidator.existById(memberId);
		commentValidator.checkWriter(commentId, memberId);
		commentRepository.updateComment(Comment.from(commentModifyRequest), commentId);
	}

	@Transactional
	public CommentResponse save(CommentCreateRequest commentCreateRequest, Long memberId) {
		memberValidator.existById(memberId);
		Long commentId = commentRepository.save(Comment.from(commentCreateRequest, memberId));
		return CommentResponse.from(commentRepository.findById(commentId).get());
	}

	@Transactional
	public void delete(Long commentId, Long memeberId) {
		memberValidator.existById(memeberId);
		commentValidator.checkWriter(commentId, memeberId);
		commentRepository.deleteById(commentId);
	}
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

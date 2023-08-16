package com.issuetrackermax.service.comment;

import static com.issuetrackermax.common.util.CommonUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.S3Exception;
import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;
import com.issuetrackermax.controller.comment.dto.response.CommentResponse;
import com.issuetrackermax.domain.comment.CommentRepository;
import com.issuetrackermax.domain.comment.CommentValidator;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.comment.entity.CommentMemberVO;
import com.issuetrackermax.domain.member.MemberRepository;
import com.issuetrackermax.domain.member.MemberValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final AmazonS3Client amazonS3Client;
	private final MemberValidator memberValidator;
	private final CommentValidator commentValidator;
	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Transactional(readOnly = true)
	public List<CommentMemberVO> findByIssueId(Long id) {
		return commentRepository.findByIssueId(id);
	}

	@Transactional
	public void modifyComment(CommentModifyRequest commentModifyRequest, Long commentId, Long memberId) {
		memberValidator.existById(memberId);
		commentValidator.checkWriter(commentId, memberId);
		commentRepository.updateComment(Comment.from(commentModifyRequest), commentId);
	}

	@Transactional
	public CommentResponse save(CommentCreateRequest commentCreateRequest, Long issueId, Long memberId) {
		memberValidator.existById(memberId);
		Long commentId = commentRepository.save(Comment.from(commentCreateRequest, issueId, memberId));
		Comment comment = commentRepository.findById(commentId);
		return CommentResponse.from(comment, memberRepository.findById(comment.getWriterId()));
	}

	@Transactional
	public void delete(Long commentId, Long memberId) {
		memberValidator.existById(memberId);
		commentValidator.checkWriter(commentId, memberId);
		commentRepository.deleteById(commentId);
	}

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

package com.issuetracker.member.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.common.config.exception.CustomHttpException;
import com.issuetracker.common.config.exception.ErrorType;
import com.issuetracker.file.application.FileService;
import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.application.dto.MemberUpdateData;
import com.issuetracker.member.domain.Member;
import com.issuetracker.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final FileService fileService;

	public List<MemberInformation> searchAuthors() {
		return MemberInformation.from(memberRepository.search());
	}

	public MemberInformation findById(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomHttpException(ErrorType.MEMBER_NOT_FOUND));
		return MemberInformation.from(member);
	}

	@Transactional
	public void updateMember(MemberUpdateData memberUpdateData) {
		Member member = memberRepository.findById(memberUpdateData.getId())
			.orElseThrow(() -> new CustomHttpException(ErrorType.MEMBER_NOT_FOUND));
		String profileUrl = getProfileUrl(member.getProfileImageUrl(), memberUpdateData.getMultipartFile());
		memberRepository.update(memberUpdateData.toMember(profileUrl));
	}

	private String getProfileUrl(String originProfileUrl, MultipartFile multipartFile) {
		if (Objects.isNull(multipartFile)) {
			return originProfileUrl;
		}

		return fileService.upload(multipartFile).getUrl();
	}
}

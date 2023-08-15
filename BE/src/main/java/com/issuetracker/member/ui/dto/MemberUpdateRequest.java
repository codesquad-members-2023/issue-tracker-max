package com.issuetracker.member.ui.dto;

import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.member.application.dto.MemberUpdateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberUpdateRequest {

	private String nickname;
	private String password;

	public MemberUpdateData toMemberUpdateData(Long id, MultipartFile multipartFile) {
		return new MemberUpdateData(
			id,
			nickname,
			password,
			multipartFile
		);
	}
}

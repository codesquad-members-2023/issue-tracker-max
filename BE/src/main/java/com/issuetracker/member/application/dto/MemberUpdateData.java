package com.issuetracker.member.application.dto;

import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberUpdateData {

	private Long id;
	private String nickname;
	private String password;
	private MultipartFile multipartFile;
}

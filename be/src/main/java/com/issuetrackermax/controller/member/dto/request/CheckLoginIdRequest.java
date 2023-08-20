package com.issuetrackermax.controller.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckLoginIdRequest {
	@NotBlank(message = "아이디를 입력해주세요.")
	@Email(message = "아이디는 이메일 형식으로 입력해주세요.")
	@Size(min = 6, max = 25, message = "아이디는 6자 이상, 25자 이하로 입력해주세요.")
	private String loginId;
}

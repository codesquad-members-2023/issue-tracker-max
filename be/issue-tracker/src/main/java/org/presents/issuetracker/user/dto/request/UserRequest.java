package org.presents.issuetracker.user.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class UserRequest {
	@NotEmpty
	@Size(min = 6, max = 16)
	private String loginId;
	@NotEmpty
	@Size(min = 6, max = 12)
	private String password;
}

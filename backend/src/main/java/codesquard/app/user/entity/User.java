package codesquard.app.user.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class User {

	private Long id;
	private String userId;
	private String email;
	private String password;
	private String avatarUrl;
}

package codesquad.issueTracker.comment.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUserVo {
	private String name;
	private String profileImg;

	@Builder
	public CommentUserVo(String name, String profileImg) {
		this.name = name;
		this.profileImg = profileImg;
	}
}

package codesquad.issueTracker.issue.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyIssueTitleRequest {
	@NotNull(message = "제목을 입력해주세요.")
	@NotBlank(message = "공백은 입력할 수 없습니다.")
	private String title;
}

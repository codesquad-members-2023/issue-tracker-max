package codesquard.app.api.response;

public abstract class ResponseMessage {

	public static final String COMMENT_SAVE_SUCCESS = "댓글 등록에 성공했습니다.";
	public static final String ISSUE_SAVE_SUCCESS = "이슈 등록에 성공했습니다.";
	public static final String IMAGE_SAVE_SUCCESS = "이미지 저장에 성공했습니다.";
	public static final String USER_REACTION_ISSUE_SAVE_SUCCESS = "이슈 사용자 반응 등록에 성공했습니다.";
	public static final String USER_REACTION_COMMENT_SAVE_SUCCESS = "댓글 사용자 반응 등록에 성공했습니다.";
	public static final String MAXIMUM_UPLOAD_SIZE_EXCEEDED = "파일의 최대 크기를 초과했습니다.";
	public static final String USER_SIGNUP_SUCCESS = "회원가입에 성공하였습니다.";
	public static final String USER_LOGIN_SUCCESS = "로그인에 성공하였습니다.";
	public static final String REFRESHTOKEN_UPDATE_SUCCESS = "Refreshtoken이 성공적으로 갱신되었습니다.";
}

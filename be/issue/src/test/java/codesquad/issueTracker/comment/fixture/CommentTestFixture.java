package codesquad.issueTracker.comment.fixture;

import codesquad.issueTracker.comment.domain.Comment;
import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.vo.CommentUserVo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class CommentTestFixture {
    public List<CommentResponseDto> dummyCommentResponseDtos() {
        return IntStream.range(1, 4)
                .mapToObj(this::makeCommentResponses)
                .collect(Collectors.toList());
    }

    private CommentResponseDto makeCommentResponses(int num) {
        Comment comment = dummyComment(num);
        CommentUserVo commentUserVo = makeCommentUser();

        return CommentResponseDto.builder()
                .id((long) num)
                .content(comment.getContent())
                .writer(commentUserVo)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public Comment dummyComment(int num) {
        return Comment.builder()
                .issueId(1L)
                .userId(1L)
                .content("comment test" + num)
                .createdAt(dummyLocalDateTime())
                .build();
    }

    private LocalDateTime dummyLocalDateTime() {
        return LocalDateTime.of(2023, 8, 12, 7, 23, 10);
    }

    private CommentUserVo makeCommentUser() {
        return CommentUserVo.builder()
                .name("sio")
                .profileImg("http://image.png")
                .build();
    }

    public CommentRequestDto dummyCommentRequestDto(int num) {
        return new CommentRequestDto("comment test" + num);
    }
    
}

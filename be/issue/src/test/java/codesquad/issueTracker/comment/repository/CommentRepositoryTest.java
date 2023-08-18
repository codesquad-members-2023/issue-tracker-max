package codesquad.issueTracker.comment.repository;

import static org.assertj.core.api.Assertions.*;

import annotation.RepositoryTest;
import codesquad.issueTracker.comment.domain.Comment;
import codesquad.issueTracker.comment.dto.CommentRequestDto;
import codesquad.issueTracker.comment.dto.CommentResponseDto;
import codesquad.issueTracker.comment.fixture.CommentTestFixture;
import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import codesquad.issueTracker.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


@RepositoryTest
class CommentRepositoryTest extends CommentTestFixture {

    private CommentRequestDto commentRequestDtoFixture1;
    private CommentRequestDto commentRequestDtoFixture2;
    private List<CommentRequestDto> commentRequestDtosFixture;
    private List<CommentResponseDto> commentResponseDtosFixture;

    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public CommentRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.commentRepository = new CommentRepository(jdbcTemplate);
        this.userRepository = new UserRepository(jdbcTemplate);
    }

    @BeforeEach
    public void setUp() {
        commentRequestDtoFixture1 = dummyCommentRequestDto(1);
        commentRequestDtoFixture2 = dummyCommentRequestDto(2);
        commentRequestDtosFixture = dummyCommentRequestDtos();
        commentResponseDtosFixture = dummyCommentResponseDtos();
    }

    @Test
    @DisplayName("DB에 댓글 생성 데이터가 제대로 들어간다.")
    public void create() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;

        //when
        Long actual = commentRepository.create(userId, issueId, commentRequestDtoFixture1).get();

        //then
        assertThat(actual).isEqualTo(1L);
    }
    @Test
    @DisplayName("이슈 id에 해당하는 댓글 목록을 반환할 수 있다.")
    public void findComments() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;

        User user = User.builder()
                .id(1L)
                .name("sio")
                .email("sio@gmail.com")
                .profileImg("http://image.png")
                .loginType(LoginType.GITHUB)
                .build();

        userRepository.insert(user);

        for (CommentRequestDto commentRequestDto : commentRequestDtosFixture) {
            commentRepository.create(userId, issueId, commentRequestDto);
        }
        //when
        List<CommentResponseDto> actual = commentRepository.findByIssueId(issueId);

        //then
        assertThat(actual.get(0).getId()).isEqualTo(commentResponseDtosFixture.get(0).getId());
    }

    @Test
    @DisplayName("DB에 댓글 수정 데이터가 제대로 반영된다.")
    public void update() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;
        commentRepository.create(userId, issueId, commentRequestDtoFixture1);

        //when
        Long updatedId = commentRepository.update(userId, commentRequestDtoFixture2).get();
        Comment actual = commentRepository.findById(updatedId).get();

        //then
        assertThat(actual.getContent()).isEqualTo(commentRequestDtoFixture2.getContent());
    }

    @Test
    @DisplayName("DB에 댓글 삭제가 제대로 반영된다.")
    public void delete() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;
        Long commentId = commentRepository.create(userId, issueId, commentRequestDtoFixture1).get();

        //when
        Long deletedId = commentRepository.deleteById(commentId).get();
        Optional<Comment> actual = commentRepository.findExistCommentById(deletedId);

        //then
        assertThat(actual.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("findById 동작 테스트")
    public void findById() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;
        Long commentId = commentRepository.create(userId, issueId, commentRequestDtoFixture1).get();

        //when
        Comment actual = commentRepository.findById(commentId).get();

        //then
        assertThat(actual.getId()).isEqualTo(commentId);
    }

    @Test
    @DisplayName("findExistCommentById 동작 테스트")
    public void findExistCommentById() throws Exception {
        //given
        Long userId = 1L;
        Long issueId = 1L;
        Long commentId = commentRepository.create(userId, issueId, commentRequestDtoFixture1).get();
        commentRepository.deleteById(commentId);

        //when
        Optional<Comment> actual = commentRepository.findExistCommentById(commentId);

        //then
        assertThat(actual.isPresent()).isEqualTo(false);
    }
}
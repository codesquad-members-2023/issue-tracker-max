package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JwtTokenRepository {

    private final NamedParameterJdbcTemplate template;

    public Optional<Member> findByRefreshToken(String refreshToken) {
        String sql = "SELECT member.id, member.email, member.name, member.password, member.profile "
                + "FROM member "
                + "JOIN refresh_token "
                + "ON member.id = refresh_token.member_id "
                + "WHERE refresh_token.refresh_token = :refreshToken";

        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                template.query(sql, Map.of("refreshToken", refreshToken), memberRowMapper())
        ));
    }

    public void saveRefreshToken(String refreshToken, Long memberId) {
        String sql = "INSERT INTO refresh_token(refresh_token, member_id) VALUES(:refreshToken, :memberId) "
                + "ON DUPLICATE KEY UPDATE refresh_token = :refreshToken";
        template.update(sql, Map.of("refreshToken", refreshToken, "memberId", memberId));
    }

    public boolean deleteRefreshToken(Long memberId) {
        String sql = "DELETE FROM refresh_token WHERE member_id = :memberId";
        int result = template.update(sql, Map.of("memberId", memberId));
        return result > 0;
    }

    private final RowMapper<Member> memberRowMapper() {
        return ((rs, rowNum) -> Member.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .password(rs.getString("password"))
                .profile(rs.getString("profile"))
                .build());
    }
}

package codesquad.issueTracker.user.repository;

import codesquad.issueTracker.user.domain.LoginType;
import codesquad.issueTracker.user.domain.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Long insert(User user) {
        String sql = "INSERT INTO users (email,password,name, login_type) VALUES (:email,:password,:name,:loginType)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("name", user.getName());
        params.addValue("loginType", user.getLoginType().getType());

        jdbcTemplate.update(sql, params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id,token_id,email,password,profile_img,name,login_type FROM USERS WHERE email = :email";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("email", email), userRowMapper)));
    }

    private final RowMapper<User> userRowMapper = (((rs, rowNum) -> User.builder()
            .id(rs.getLong("id"))
            .tokenId(rs.getLong("token_id"))
            .email(rs.getString("email"))
            .password(rs.getString("password"))
            .profileImg(rs.getString("profile_img"))
            .name(rs.getString("name"))
            .loginType(LoginType.findByTypeString(rs.getString("login_type")))
            .build()
    ));
}

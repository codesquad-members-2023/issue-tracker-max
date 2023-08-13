package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private static final byte[] secret = "codesquad-issue-tracker-max-01-gyeonggidoidle".getBytes();
    private final Key key = Keys.hmacShaKeyFor(secret);

    public String createToken(Map<String, Object> claims, Date expireDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Jwt createJwt(Map<String, Object> claims) {
        String accessToken = createToken(claims,getExpireDateAccessToken());
        String refreshToken = createToken(new HashMap<>(), getExpireDateRefreshToken());
        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Jwt reissueAccessToken(Map<String, Object> claims, String refreshToken) {
        String accessToken = createToken(claims, getExpireDateAccessToken());
        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Date getExpireDateAccessToken() {
        long expireTimeMils = 1000L * 60 * 60; // 1시간
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }

    public Date getExpireDateRefreshToken() {
        long expireTimeMils = 1000L * 60 * 60 * 24 * 60; // 60일
        return new Date(System.currentTimeMillis() + expireTimeMils);
    }
}

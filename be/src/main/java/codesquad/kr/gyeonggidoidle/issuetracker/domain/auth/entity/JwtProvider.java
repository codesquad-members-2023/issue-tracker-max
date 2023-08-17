package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private final Key key;
    private final Date accessExpireDate;
    private final Date refreshExpireDate;

    public JwtProvider(@Value("${jwt.secret}") String secret, @Value("${jwt.access-expiration-milliseconds}") long accessExpireMillis,
                       @Value("${jwt.refresh-expiration-milliseconds}") long refreshExpireMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpireDate = new Date(System.currentTimeMillis() + accessExpireMillis);
        this.refreshExpireDate = new Date(System.currentTimeMillis() + refreshExpireMillis);
    }

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
        String accessToken = createToken(claims,accessExpireDate);
        String refreshToken = createToken(new HashMap<>(), refreshExpireDate);
        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Jwt reissueAccessToken(Map<String, Object> claims, String refreshToken) {
        String accessToken = createToken(claims, accessExpireDate);
        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

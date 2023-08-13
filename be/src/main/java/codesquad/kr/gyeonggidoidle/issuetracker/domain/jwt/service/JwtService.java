package codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.controller.response.JwtTokenType;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.repository.JwtRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.LoginCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalJwtTokenException;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalPasswordException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtRepository jwtRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public Jwt login(LoginCondition condition) {
        Member member = memberRepository.findByEmail(condition.getEmail());
        if (!verifyPassword(member, condition.getPassword())) {
            throw new IllegalPasswordException();
        }
        Jwt jwt = jwtProvider.createJwt(generateMemberClaims(member));
        jwtRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());
        return jwt;
    }

    @Transactional
    public Jwt reissueAccessToken(String refreshToken) {
        Optional<Member> optionalMember = jwtRepository.findByRefreshToken(refreshToken);
        Member member = optionalMember.orElseThrow(() -> new IllegalJwtTokenException(JwtTokenType.REFRESH));
        return jwtProvider.reissueAccessToken(generateMemberClaims(member), refreshToken);
    }

    public void logout(Long memberId ) {
        if (!jwtRepository.deleteRefreshToken(memberId)) {
            throw new IllegalJwtTokenException(JwtTokenType.REFRESH);
        }
    }

    private Map<String, Object> generateMemberClaims(Member member) {
        return Map.of("memberId", member.getId());
    }

    private boolean existMember(Member member) {
        return member != null;
    }

    private boolean verifyPassword(Member member, String password) {
        return existMember(member) && Objects.equals(password, member.getPassword());
    }
}

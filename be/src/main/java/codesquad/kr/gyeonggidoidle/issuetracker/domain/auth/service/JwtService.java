package codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.controller.response.JwtTokenType;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.Jwt;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.repository.JwtTokenRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.condition.LoginCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.entity.JwtProvider;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.auth.service.information.JwtLoginInformation;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalJwtTokenException;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.IllegalPasswordException;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.MemberNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class JwtService {

    private final JwtTokenRepository jwtTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public JwtLoginInformation login(LoginCondition condition) {
        Member member = memberRepository.findByEmail(condition.getEmail());
        if (!verifyPassword(member, condition.getPassword())) {
            throw new IllegalPasswordException();
        }
        Jwt jwt = jwtProvider.createJwt(generateMemberClaims(member));
        jwtTokenRepository.saveRefreshToken(jwt.getRefreshToken(), member.getId());
        return JwtLoginInformation.from(member, jwt);
    }

    public Jwt reissueAccessToken(String refreshToken) {
        Optional<Member> optionalMember = jwtTokenRepository.findByRefreshToken(refreshToken);
        Member member = optionalMember.orElseThrow(() -> new IllegalJwtTokenException(JwtTokenType.REFRESH));
        return jwtProvider.reissueAccessToken(generateMemberClaims(member), refreshToken);
    }

    public void logout(Long memberId ) {
        if (!jwtTokenRepository.deleteRefreshToken(memberId)) {
            throw new IllegalJwtTokenException(JwtTokenType.REFRESH);
        }
    }

    private Map<String, Object> generateMemberClaims(Member member) {
        return Map.of("memberId", member.getId());
    }

    private boolean existMember(Member member) {
        if (member == null) {
            throw new MemberNotFoundException();
        }
        return true;
    }

    private boolean verifyPassword(Member member, String password) {
        return existMember(member) && Objects.equals(password, member.getPassword());
    }
}

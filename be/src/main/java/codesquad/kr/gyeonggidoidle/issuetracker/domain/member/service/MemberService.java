package codesquad.kr.gyeonggidoidle.issuetracker.domain.member.service;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.jwt.service.condition.SignUpCondition;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.Member;
import codesquad.kr.gyeonggidoidle.issuetracker.domain.member.repository.MemberRepository;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.MemberDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signUp(SignUpCondition condition) {
        String email = condition.getEmail();
        if (existMember(memberRepository.findByEmail(email))) {
            throw new MemberDuplicationException(email);
        }
        memberRepository.saveMember(SignUpCondition.to(condition));
    }

    private boolean existMember(Member member) {
        return member != null;
    }
}

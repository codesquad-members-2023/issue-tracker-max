package com.codesquad.issuetracker.api.member.repository;

import com.codesquad.issuetracker.api.filter.dto.MemberFilter;
import com.codesquad.issuetracker.api.member.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Long> save(Member member, String providerName);

    List<MemberFilter> findFiltersBy(Long organizationId);

    Optional<Long> findMemberIdByEmail(String email);

    Optional<Member> findMemberByEmail(String email);

    boolean isNicknameExists(String nickname);

}

package com.issuetracker.member.domain;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

	boolean existById(Long id);

	boolean existByIds(List<Long> Ids);

	List<Member> search();

	Optional<Member> findById(Long id);

	int update(Member member);
}

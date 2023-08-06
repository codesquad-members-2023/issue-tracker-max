package com.issuetracker.member.domain;

import java.util.List;

public interface MemberRepository {

	boolean existById(Long id);

	boolean existByIds(List<Long> Ids);
}

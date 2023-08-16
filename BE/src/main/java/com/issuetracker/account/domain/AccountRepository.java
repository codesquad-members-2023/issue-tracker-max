package com.issuetracker.account.domain;

public interface AccountRepository {

	Account findByEmail(String email);

	Account findByEmailAndPassword(Account account);

	Account findByMemberId(Long memberId);

	Account findByOauthId(Long oauthId);

	Long save(Account account);

	boolean existByEmail(String email);

	void saveGitMember(Long memberId, Long oauthId);

}

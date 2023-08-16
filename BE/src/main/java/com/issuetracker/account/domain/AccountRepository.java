package com.issuetracker.account.domain;

public interface AccountRepository {

	Account findByEmail(String email);

	Account findByEmailAndPassword(Account account);

	Account findByMemberId(Long memberId);

	Long save(Account account);

	boolean existByEmail(String email);

}

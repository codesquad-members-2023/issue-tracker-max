package com.issuetracker.account.domain;

public interface AccountRepository {

	Account findByEmail(String email);

	Long save(Account account);

	boolean existByEmail(String email);

}

package com.issuetracker.account.domain;

public interface AccountRepository {

	Account findByEmail(String email);

}

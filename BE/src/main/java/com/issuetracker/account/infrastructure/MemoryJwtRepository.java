package com.issuetracker.account.infrastructure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.issuetracker.account.domain.JwtRefreshToken;

@Repository
public class MemoryJwtRepository {

	private static final Map<Long, JwtRefreshToken> refreshTokenStorage = new ConcurrentHashMap<>();

	public void save(JwtRefreshToken jwtRefreshToken) {
		refreshTokenStorage.put(jwtRefreshToken.getMemberId(), jwtRefreshToken);
	}

	public JwtRefreshToken get(Long memberId) {
		return refreshTokenStorage.get(memberId);
	}

	public void removeRefreshToken(Long memberId) {
		refreshTokenStorage.remove(memberId);
	}

	public int removeAllExpiredRefreshTokens() {
		int count = 0;
		for (Long memberId : refreshTokenStorage.keySet()) {
			if (refreshTokenStorage.get(memberId).isExpired()) {
				refreshTokenStorage.remove(memberId);
				count++;
			}
		}
		return count;
	}
}

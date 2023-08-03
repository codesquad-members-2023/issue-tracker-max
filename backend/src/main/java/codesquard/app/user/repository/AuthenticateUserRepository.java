package codesquard.app.user.repository;

import codesquard.app.jwt.Jwt;
import codesquard.app.user.entity.User;

public interface AuthenticateUserRepository {

	void updateRefreshToken(User user, Jwt jwt);

	boolean existRefreshToken(User user);

	void saveRefreshToken(User user, Jwt jwt);
}

package codesquard.app.user.repository;

import java.util.List;

import codesquard.app.user.entity.User;

public interface UserRepository {

	Long save(User user);

	List<User> findAll();

	User findById(Long id);

	Long modify(User user);

	Long deleteById(Long id);

	boolean isExistLoginId(User user);

	boolean isExistEmail(User user);

	User findByLoginIdAndPassword(User user);

	User findByRefreshToken(String refreshToken);

	User findByEmail(User user);
}

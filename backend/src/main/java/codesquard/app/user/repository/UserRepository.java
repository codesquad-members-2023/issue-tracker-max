package codesquard.app.user.repository;

import java.util.List;

import codesquard.app.user.entity.User;

public interface UserRepository {

	Long save(User user);

	List<User> findAll();

	User findById(Long id);

	Long modify(User user);

	Long deleteById(Long id);

	boolean existLoginId(User user);

	boolean existEmail(User user);
}

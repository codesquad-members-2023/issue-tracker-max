package codesquard.app.label.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import codesquard.app.IntegrationTestSupport;
import codesquard.app.label.dto.request.LabelSaveRequest;
import codesquard.app.label.dto.request.LabelUpdateRequest;
import codesquard.app.label.repository.LabelRepository;
import codesquard.app.user.entity.User;
import codesquard.app.user.repository.UserRepository;

class LabelServiceTest extends IntegrationTestSupport {

	@Autowired
	private LabelService labelService;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JdbcTemplate template;

	@BeforeEach
	void setUp() {
		template.update("SET FOREIGN_KEY_CHECKS = 0");
		template.update("TRUNCATE TABLE `label`");
		template.update("TRUNCATE TABLE `user`");
		template.update("SET FOREIGN_KEY_CHECKS = 1");
	}

	@DisplayName("라벨을 등록한다.")
	@Test
	void saveLabel() {
		// given
		createUserFixture();
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest("이름", "dark", "#102022", "설명");

		// when
		Long savedId = labelService.saveLabel(labelSaveRequest, 1L);

		// then
		assertThat(savedId).isEqualTo(1L);
	}

	@DisplayName("라벨을 수정한다.")
	@Test
	void updateLabel() {
		// given
		createUserFixture();
		Long labelId = createLabelFixture().get();
		LabelUpdateRequest labelUpdateRequest = new LabelUpdateRequest("바뀐 이름", "light", "#101010", "바뀐 설명");

		// when
		Long updatedId = labelService.updateLabel(labelId, 1L, labelUpdateRequest);

		// then
		assertThat(updatedId).isEqualTo(labelId);
	}

	@DisplayName("라벨을 삭제한다.")
	@Test
	void deleteLabel() {
		// given
		createUserFixture();
		Long labelId = createLabelFixture().get();

		// when
		Long deletedId = labelService.deleteLabel(labelId, 1L);

		// then
		assertThat(deletedId).isEqualTo(labelId);
	}

	private void createUserFixture() {
		User user = new User(null, "sully", "sully1234@gmail.com", "qwe123", "url path");
		userRepository.save(user);
	}

	private Optional<Long> createLabelFixture() {
		LabelSaveRequest labelSaveRequest = new LabelSaveRequest("이름", "dark", "#102022", "설명");
		return labelRepository.save(LabelSaveRequest.toEntity(labelSaveRequest, 1L));
	}
}

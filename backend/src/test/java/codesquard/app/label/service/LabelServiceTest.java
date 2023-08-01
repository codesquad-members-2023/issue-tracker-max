package codesquard.app.label.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import codesquard.app.label.dto.LabelRequest;
import codesquard.app.label.entity.Label;
import codesquard.app.label.repository.LabelRepository;

class LabelServiceTest {
	@InjectMocks
	private LabelService labelService;

	@Mock
	private LabelRepository labelRepository;

	@DisplayName("saveLabel()을 통해 Label을 저장할 수 있다.")
	@Test
	void saveLabel() throws Exception {
		// given
		given(labelRepository.save(any()))
			.willReturn(Optional.ofNullable(dummyLabel().getId()));

		// when
		labelService.saveLabel(dummyLabelRequestDto());

		// then
		assertThat(1L).isEqualTo(dummyLabel().getId());
	}

	private Label dummyLabel() {
		return new Label(1L, "라벨명", "#000000", "#ffffff", "내용입니다.");
	}

	private LabelRequest dummyLabelRequestDto() {
		return new LabelRequest("라벨명", "#000000", "#ffffff", "내용입니다.");
	}
}

package com.issuetrackermax.service.label;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.issuetrackermax.common.exception.ApiException;
import com.issuetrackermax.common.exception.domain.LabelException;
import com.issuetrackermax.controller.label.dto.request.LabelModifyRequest;
import com.issuetrackermax.controller.label.dto.request.LabelPostRequest;
import com.issuetrackermax.controller.label.dto.response.LabelDetailResponse;
import com.issuetrackermax.domain.IntegrationTestSupport;
import com.issuetrackermax.domain.label.LabelRepository;
import com.issuetrackermax.domain.label.entity.Label;
import com.issuetrackermax.util.DatabaseCleaner;

class LabelServiceTest extends IntegrationTestSupport {

	@Autowired
	LabelService labelService;
	@Autowired
	LabelRepository labelRepository;
	@Autowired
	DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleaner() {
		databaseCleaner.execute();
	}

	@DisplayName("저장된 레이블들을 확인한다.")
	@Test
	void getLabelList() {
		// given
		Long labelId1 = labelService.save(new LabelPostRequest("title1", "description1", "0#1111", "0#2222"));
		Long labelId2 = labelService.save(new LabelPostRequest("title2", "description2", "0#3333", "0#4444"));

		// then
		List<LabelDetailResponse> labelList = labelService.getLabelList();

		// when
		assertAll(
			() -> assertThat(labelList.get(0).getId()).isEqualTo(labelId1),
			() -> assertThat(labelList.get(0).getTitle()).isEqualTo("title1"),
			() -> assertThat(labelList.get(0).getDescription()).isEqualTo("description1"),
			() -> assertThat(labelList.get(0).getTextColor()).isEqualTo("0#1111"),
			() -> assertThat(labelList.get(0).getBackgroundColor()).isEqualTo("0#2222"),
			() -> assertThat(labelList.get(1).getId()).isEqualTo(labelId2),
			() -> assertThat(labelList.get(1).getTitle()).isEqualTo("title2"),
			() -> assertThat(labelList.get(1).getDescription()).isEqualTo("description2"),
			() -> assertThat(labelList.get(1).getTextColor()).isEqualTo("0#3333"),
			() -> assertThat(labelList.get(1).getBackgroundColor()).isEqualTo("0#4444")
		);

	}

	@DisplayName("레이블을 저장하고, 저장된 값을 확인한다.")
	@Test
	void save() {
		// given
		LabelPostRequest labelPostRequest = new LabelPostRequest("title1", "description1", "0#1111", "0#2222");
		// when
		Long id = labelService.save(labelPostRequest);

		// then
		Label label = labelRepository.findById(id);
		assertAll(
			() -> assertThat(label.getId()).isEqualTo(id),
			() -> assertThat(label.getTitle()).isEqualTo("title1"),
			() -> assertThat(label.getDescription()).isEqualTo("description1"),
			() -> assertThat(label.getTextColor()).isEqualTo("0#1111"),
			() -> assertThat(label.getBackgroundColor()).isEqualTo("0#2222")
		);

	}

	@DisplayName("레이블을 저장하고,같은 이름의 레이블을 저장하면 오류가 발생한다..")
	@Test
	void saveDuplicateTitle() {
		// given
		LabelPostRequest labelPostRequest = new LabelPostRequest("title1", "description1", "0#1111", "0#2222");
		Long id = labelService.save(labelPostRequest);
		LabelPostRequest labelPostRequest2 = new LabelPostRequest("title1", "description1", "0#1111", "0#2222");

		// when & then
		assertThatThrownBy(() -> labelService.save(labelPostRequest2))
			.isInstanceOf(ApiException.class)
			.satisfies(exception -> {
				ApiException apiException = (ApiException)exception;
				assertThat(apiException.getCustomException()).isInstanceOf(LabelException.class);
			});
	}

	@DisplayName("저장된 레이블을 수정하고, 수정된 값을 확인한다.")
	@Test
	void update() {
		// given
		LabelPostRequest labelPostRequest = new LabelPostRequest("title1", "description1", "0#1111", "0#2222");
		Long savedId = labelService.save(labelPostRequest);
		LabelModifyRequest labelModifyRequest = new LabelModifyRequest("title2", "description2", "0#3333", "0#4444");

		// when
		labelService.update(savedId, labelModifyRequest);

		// then
		Label label = labelRepository.findById(savedId);
		assertAll(
			() -> assertThat(label.getId()).isEqualTo(savedId),
			() -> assertThat(label.getTitle()).isEqualTo("title2"),
			() -> assertThat(label.getDescription()).isEqualTo("description2"),
			() -> assertThat(label.getTextColor()).isEqualTo("0#3333"),
			() -> assertThat(label.getBackgroundColor()).isEqualTo("0#4444")
		);
	}

	@DisplayName("Label을 삭제한다.")
	@Test
	void delete() {
		// given
		LabelPostRequest labelPostRequest = new LabelPostRequest("title1", "description1", "0#1111", "0#2222");
		Long savedId = labelService.save(labelPostRequest);

		// when
		labelService.delete(savedId);

		// then
		assertThat(labelRepository.existByIds(List.of(savedId))).isFalse();
	}
}

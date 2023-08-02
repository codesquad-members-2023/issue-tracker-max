package com.issuetracker.unit.validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.issuetracker.config.exception.LabelNotFoundException;
import com.issuetracker.config.exception.MemberNotFoundException;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.label.infrastructure.LabelRepository;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.member.infrastructure.MemberRepository;
import com.issuetracker.util.MockTest;

@MockTest
public class LabelValidatorTest {

	@InjectMocks
	private LabelValidator labelValidator;

	@Mock
	private LabelRepository labelRepository;

	@ParameterizedTest
	@MethodSource("providerListLong")
	void 라벨들이_존재하는지_검증한다(List<Long> ids) {
		// given
		given(labelRepository.existByIds(any())).willReturn(true);

		// then
		Assertions.assertDoesNotThrow(() -> labelValidator.verifyLabels(ids));
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 라벨들이_존재하는지_검증시_아이디들이_비어있는_경우_동작되지_않는다(List<Long> ids) {
		// then
		Assertions.assertDoesNotThrow(() -> labelValidator.verifyLabels(ids));
		then(labelRepository).should(Mockito.never()).existByIds(null);
	}

	@Test
	void 라벨들_중에_존재하지_않으면_에러를_반환한다() {
		// given
		given(labelRepository.existByIds(any())).willReturn(false);

		// then
		Assertions.assertThrows(LabelNotFoundException.class,
			() -> labelValidator.verifyLabels(Arrays.asList(1L, null)));
	}

	private static Stream<Arguments> providerListLong() {
		return Stream.of(
			Arguments.of(Arrays.asList(1L, null)),
			Arguments.of(Arrays.asList(1L, 2L))
		);
	}
}

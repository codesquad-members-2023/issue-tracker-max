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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.issuetracker.config.exception.MemberNotFoundException;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.member.infrastructure.MemberRepository;
import com.issuetracker.util.MockTest;

@MockTest
public class MemberValidatorTest {

	@InjectMocks
	private MemberValidator memberValidator;

	@Mock
	private MemberRepository memberRepository;

	@Test
	void 회원이_존재하는지_검증한다() {
		// given
		given(memberRepository.existById(any())).willReturn(true);

		// then
		Assertions.assertDoesNotThrow(() -> memberValidator.verifyMember(1L));
	}

	@Test
	void 회원이_존재하는지_검증시_아이디가_null인_경우_동작되지_않는다() {
		// then
		Assertions.assertDoesNotThrow(() -> memberValidator.verifyMember(null));
		then(memberRepository).should(Mockito.never()).existById(null);
	}

	@ParameterizedTest
	@MethodSource("providerListLong")
	void 회원들이_존재하는지_검증한다(List<Long> ids) {
		// given
		given(memberRepository.existByIds(any())).willReturn(true);

		// then
		Assertions.assertDoesNotThrow(() -> memberValidator.verifyMembers(ids));
	}

	@ParameterizedTest
	@NullAndEmptySource
	void 회원들이_존재하는지_검증시_아이디들이_비어있는_경우_동작되지_않는다(List<Long> ids) {
		// then
		Assertions.assertDoesNotThrow(() -> memberValidator.verifyMembers(ids));
		then(memberRepository).should(Mockito.never()).existByIds(null);
	}


	@Test
	void 해당_회원이_존재하지_않으면_에러를_반환한다() {
		// given
		given(memberRepository.existById(any())).willReturn(false);

		// then
		Assertions.assertThrows(MemberNotFoundException.class,
			() -> memberValidator.verifyMember(1L));
	}

	@Test
	void 회원들_중에_존재하지_않으면_에러를_반환한다() {
		// given
		given(memberRepository.existByIds(any())).willReturn(false);

		// then
		Assertions.assertThrows(MemberNotFoundException.class,
			() -> memberValidator.verifyMembers(Arrays.asList(1L, null)));
	}

	private static Stream<Arguments> providerListLong() {
		return Stream.of(
			Arguments.of(Arrays.asList(1L, null)),
			Arguments.of(Arrays.asList(1L, 2L))
		);
	}
}

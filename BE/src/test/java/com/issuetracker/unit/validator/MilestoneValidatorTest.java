package com.issuetracker.unit.validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.issuetracker.config.exception.MilestoneNotFoundException;
import com.issuetracker.milestone.application.MilestoneValidator;
import com.issuetracker.milestone.infrastructure.MilestoneRepository;
import com.issuetracker.util.MockTest;

@MockTest
public class MilestoneValidatorTest {

	@InjectMocks
	private MilestoneValidator milestoneValidator;

	@Mock
	private MilestoneRepository milestoneRepository;

	@Test
	void 아이디로_마일스톤이_존재하는지_검증한다() {
		// given
		given(milestoneRepository.existById(any())).willReturn(true);

		// then
		Assertions.assertDoesNotThrow(() -> milestoneValidator.verifyMilestone(1L));
	}

	@Test
	void 마일스톤이_존재하는지_검증시_아이디가_null인_경우_동작되지_않는다() {
		// then
		Assertions.assertDoesNotThrow(() -> milestoneValidator.verifyMilestone(null));
		then(milestoneRepository).should(Mockito.never()).existById(null);
	}

	@Test
	void 해당_마일스톤이_존재하지_않으면_에러를_반환한다() {
		// given
		given(milestoneRepository.existById(any())).willReturn(false);

		// then
		Assertions.assertThrows(MilestoneNotFoundException.class,
			() -> milestoneValidator.verifyMilestone(1L));
	}
}

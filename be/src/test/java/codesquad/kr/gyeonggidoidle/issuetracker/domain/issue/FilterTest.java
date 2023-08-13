package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class FilterTest {

    @DisplayName("FilterCondition에서 Filter가 만들어진다.")
    @Test
    void createFilterFromFilterCondition() {
        //given
        String filterCondition = "is:open author:nag milestone:마일스톤1";

        //when
        Filter actual = Filter.from(filterCondition);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getIsOpen()).isTrue();
            assertions.assertThat(actual.getLabel()).isNull();
            assertions.assertThat(actual.getAuthor()).isEqualTo("nag");
            assertions.assertThat(actual.getMilestone()).isEqualTo("마일스톤1");
        });
    }

    @DisplayName("FilterCondition에 is 필드가 없으면 Filter의 isopen은 null이다.")
    @Test
    void createIsOpenNullFilter() {
        //given
        String filterCondition = "author:nag milestone:마일스톤1";

        //when
        Filter actual = Filter.from(filterCondition);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getIsOpen()).isNull();
        });
    }

}

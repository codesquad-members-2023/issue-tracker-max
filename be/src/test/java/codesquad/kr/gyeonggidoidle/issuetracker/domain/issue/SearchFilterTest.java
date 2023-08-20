package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class SearchFilterTest {

    @DisplayName("FilterCondition에서 Filter가 만들어진다.")
    @Test
    void createFilterFromFilterCondition() {
        //given
        String filterCondition = "is:open author:nag milestone:마일스톤1";

        //when
        SearchFilter actual = SearchFilter.from(filterCondition);

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
        SearchFilter actual = SearchFilter.from(filterCondition);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getIsOpen()).isNull();
        });
    }

    @DisplayName("FilterCondition을 파싱하다가 예외가 발생하면 NULL_SEARCH_FILTER를 반환한다.")
    @Test
    void createNullFilter() {
        //given
        String filterCondition = "author:nag milestone:마일스톤 1";

        //when
        SearchFilter actual = SearchFilter.from(filterCondition);

        //then
        assertSoftly(assertions -> {
            assertions.assertThat(actual.getIsOpen()).isNull();
            assertions.assertThat(actual.getAssignee()).isNull();
            assertions.assertThat(actual.getAuthor()).isNull();
            assertions.assertThat(actual.getMilestone()).isNull();
            assertions.assertThat(actual.getLabel()).isNull();
        });
    }
}
